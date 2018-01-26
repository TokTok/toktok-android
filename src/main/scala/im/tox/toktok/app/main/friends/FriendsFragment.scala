package im.tox.toktok.app.main.friends

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v4.app.{ Fragment, FragmentActivity }
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams
import android.view.{ LayoutInflater, ViewGroup, WindowManager }
import android.widget.LinearLayout
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.TypedBundleKey._
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.MainActivityHolder
import im.tox.toktok.app.call.CallActivity
import im.tox.toktok.app.domain.Friend
import im.tox.toktok.app.message_activity.MessageActivity
import im.tox.toktok.{ BundleKey, TR }

final class FriendsFragment extends Fragment with FriendItemClicks {

  private var mFriendsRecyclerAdapter: FriendsRecyclerHeaderAdapter = null
  private implicit def activity: FragmentActivity = getActivity

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): LinearLayout = {
    val view = inflater.inflate(TR.layout.fragment_home_friends, container, false)
    val activity = getActivity.asInstanceOf[AppCompatActivity]

    //Recycler View

    val friendsRecycler = view.findView(TR.home_friends_recycler)

    val layoutManager = new LinearLayoutManager(activity.getBaseContext)
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
    friendsRecycler.setLayoutManager(layoutManager)

    val friends = for {
      friend <- Seq(
        Friend.bart,
        Friend.lorem,
        Friend.jane,
        Friend.john
      )
      friend <- (1 to 50).map(n => friend.copy(userName = friend.userName + n))
    } yield {
      friend
    }

    mFriendsRecyclerAdapter = new FriendsRecyclerHeaderAdapter(friends, this)

    friendsRecycler.setAdapter(mFriendsRecyclerAdapter)
    friendsRecycler.setHasFixedSize(true)
    friendsRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriendsRecyclerAdapter))

    view
  }

  override def startOverLayFriend(friendPosition: Int): Unit = {
    val layout = getActivity.getLayoutInflater.inflate(TR.layout.overlay_contacts)
    val params = new WindowManager.LayoutParams(
      LayoutParams.MATCH_PARENT,
      LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
      PixelFormat.TRANSLUCENT
    )
    getActivity.getWindowManager.addView(layout, params)
    getActivity.asInstanceOf[MainActivityHolder].setAddContactPopup(layout)

    var actionBarHeight = 0

    val tv = new TypedValue()
    if (getActivity.getTheme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources.getDisplayMetrics)
    }

    layout.start(getActivity, mFriendsRecyclerAdapter.getItem(friendPosition), actionBarHeight)
  }

  override def startCall(friendPosition: Int): Unit = {
    val friend = mFriendsRecyclerAdapter.getItem(friendPosition)

    getActivity.startActivity(new Intent(activity, classOf[CallActivity]).putExtras(SBundle(
      BundleKey.contactName -> friend.userName,
      BundleKey.contactColorPrimary -> friend.color,
      BundleKey.contactPhotoReference -> friend.photoReference
    )))
  }

  override def startMessage(friendPosition: Int): Unit = {
    val friend = mFriendsRecyclerAdapter.getItem(friendPosition)

    getActivity.startActivity(new Intent(activity, classOf[MessageActivity]).putExtras(SBundle(
      BundleKey.messageTitle -> friend.userName,
      BundleKey.contactColorPrimary -> friend.color,
      BundleKey.contactColorStatus -> friend.secondColor,
      BundleKey.imgResource -> friend.photoReference,
      BundleKey.messageType -> 0
    )))
  }

}
