package im.tox.toktok.app.main.friends;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;

import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.MainActivityHolder;
import im.tox.toktok.app.call.CallActivity;
import im.tox.toktok.app.domain.Friend;
import im.tox.toktok.app.message_activity.MessageActivity;

import static im.tox.toktok.app.TypedBundleKey.SBundle;

public final class FriendsFragment extends Fragment implements FriendItemClicks {

  private FriendsRecyclerHeaderAdapter mFriendsRecyclerAdapter = null;

  @Override
  public LinearLayout onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
    LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_home_friends, container, false);
    AppCompatActivity activity = (AppCompatActivity) getActivity();

    //Recycler View

    RecyclerView friendsRecycler = view.findViewById(R.id.home_friends_recycler);

    LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getBaseContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    friendsRecycler.setLayoutManager(layoutManager);

    ArrayList<Friend> friends = new ArrayList<>();
    for (Friend friend : Arrays.asList(
            Friend.bart,
            Friend.lorem,
            Friend.jane,
            Friend.john
    )) {
      for (int n = 1; n <= 50; n++) {
        friends.add(new Friend(
                friend.id,
                friend.userName + n,
                friend.userMessage,
                friend.userStatus,
                friend.color,
                friend.secondColor,
                friend.photoReference
        ));
      }
    }

    mFriendsRecyclerAdapter = new FriendsRecyclerHeaderAdapter(friends, this);

    friendsRecycler.setAdapter(mFriendsRecyclerAdapter);
    friendsRecycler.setHasFixedSize(true);
    friendsRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriendsRecyclerAdapter));

    return view;
  }

  @Override
  public void startOverLayFriend(int friendPosition) {
    SlideInContactsLayout layout = (SlideInContactsLayout) getActivity().getLayoutInflater().inflate(R.layout.overlay_contacts, null, false);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
      LayoutParams.MATCH_PARENT,
      LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
      PixelFormat.TRANSLUCENT
    );
    final MainActivityHolder activity = (MainActivityHolder) getActivity();
    activity.getWindowManager().addView(layout, params);
    activity.setAddContactPopup(layout);

    int actionBarHeight = 0;

    TypedValue tv = new TypedValue();
    if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
    }

    layout.start(activity, mFriendsRecyclerAdapter.getItem(friendPosition), actionBarHeight);
  }

  @Override
  public void startCall(int friendPosition) {
    Friend friend = mFriendsRecyclerAdapter.getItem(friendPosition);

    getActivity().startActivity(new Intent(getActivity(), CallActivity.class).putExtras(SBundle(
      BundleKey.contactName().map(friend.userName),
      BundleKey.contactColorPrimary().map(friend.color),
      BundleKey.contactPhotoReference().map(friend.photoReference)
    )));
  }

  @Override
  public void startMessage(int friendPosition) {
    Friend friend = mFriendsRecyclerAdapter.getItem(friendPosition);

    getActivity().startActivity(new Intent(getActivity(), MessageActivity.class).putExtras(SBundle(
      BundleKey.messageTitle().map(friend.userName),
      BundleKey.contactColorPrimary().map(friend.color),
      BundleKey.contactColorStatus().map(friend.secondColor),
      BundleKey.imgResource().map(friend.photoReference),
      BundleKey.messageType().map(0)
    )));
  }

}
