package im.tox.toktok.app.message_activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.Arrays;
import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.call.CallActivity;
import im.tox.toktok.app.domain.Friend;
import im.tox.toktok.app.main.friends.FriendItemClicks;
import im.tox.toktok.app.main.friends.FriendsRecyclerHeaderAdapter;
import im.tox.toktok.app.main.friends.SlideInContactsLayout;
import im.tox.toktok.app.new_message.NewMessageActivity;

import static im.tox.toktok.app.TypedBundleKey.SBundle;

public final class MessageGroupContacts extends AppCompatActivity implements FriendItemClicks {

    private FriendsRecyclerHeaderAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group_members);

        final Bundle bundle = getIntent().getExtras();

        int colorPrimary = BundleKey.colorPrimary().get(bundle);
        int colorStatus = BundleKey.colorPrimaryStatus().get(bundle);

        Toolbar mToolbar = this.findViewById(R.id.message_group_members_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.message_group_contacts));
        mToolbar.setBackgroundColor(colorPrimary);
        getWindow().setStatusBarColor(colorStatus);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton mFAB = this.findViewById(R.id.message_group_members_fab);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageGroupContacts.this, NewMessageActivity.class).putExtras(bundle));
            }
        });

        RecyclerView mRecycler = this.findViewById(R.id.message_group_members_recycler);

        List<Friend> friends = Arrays.asList(Friend.lorem, Friend.john);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecycler.setLayoutManager(mLayoutManager);

        adapter = new FriendsRecyclerHeaderAdapter(friends, this);
        mRecycler.setAdapter(adapter);
        mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void startOverLayFriend(int friendPosition) {
        SlideInContactsLayout layout = (SlideInContactsLayout) getLayoutInflater().inflate(
                R.layout.overlay_contacts, null, false);
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
        getWindowManager().addView(layout, params);

        TypedValue tv = new TypedValue();
        int actionBarHeight = getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true) ?
                TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics()) : 0;

        layout.start(this, adapter.getItem(friendPosition), actionBarHeight);
    }

    @Override
    public void startCall(int friendPosition) {
        Friend friend = adapter.getItem(friendPosition);

        startActivity(new Intent(this, CallActivity.class).putExtras(SBundle(
                BundleKey.contactName().map(friend.userName),
                BundleKey.contactColorPrimary().map(friend.color),
                BundleKey.contactPhotoReference().map(friend.photoReference)
        )));
    }

    @Override
    public void startMessage(int friendPosition) {
        Friend friend = adapter.getItem(friendPosition);

        startActivity(new Intent(this, MessageActivity.class).putExtras(SBundle(
                BundleKey.messageTitle().map(friend.userName),
                BundleKey.contactColorPrimary().map(friend.color),
                BundleKey.contactColorStatus().map(friend.secondColor),
                BundleKey.imgResource().map(friend.photoReference),
                BundleKey.messageType().map(0)
        )));
    }

}
