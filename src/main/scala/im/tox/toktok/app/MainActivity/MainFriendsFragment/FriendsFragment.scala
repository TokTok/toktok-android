package im.tox.toktok.app.MainActivity.MainFriendsFragment

import android.content.{Intent, Context}
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams
import android.view.{LayoutInflater, View, ViewGroup, WindowManager}
import android.widget.FrameLayout
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.CallActivity.CallActivity
import im.tox.toktok.app.{MainActivityHolder, Friend}

import scala.collection.mutable.ListBuffer

class FriendsFragment extends Fragment with FriendItemClicks {

  var mFriends_Recycler_Adapter: FriendsRecyclerHeaderAdapter = null

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.fragment_home_friends, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

    //Recycler View

    val mFriends_Recycler: RecyclerView = view.findViewById(R.id.home_friends_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
    mFriends_Recycler.setLayoutManager(mLayoutManager)

    val a = ListBuffer(Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john)

    mFriends_Recycler_Adapter = new FriendsRecyclerHeaderAdapter(a, this)

    mFriends_Recycler.setAdapter(mFriends_Recycler_Adapter)
    mFriends_Recycler.setHasFixedSize(true)
    mFriends_Recycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))

    return view
  }


  def startOverLayFriend(friendPosition: Int): Unit = {


    val layout = getActivity.getLayoutInflater.inflate(R.layout.overlay_contacts, null).asInstanceOf[SlideInContactsLayout]
    val params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS , PixelFormat.TRANSLUCENT)
    val window = getActivity.getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]

    getActivity.asInstanceOf[MainActivityHolder].setActiveActivity(layout)

    window.addView(layout, params)

    var actionBarHeight = 0;

    val tv = new TypedValue();
    if (getActivity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
    }

    layout.start(getActivity,mFriends_Recycler_Adapter.getItem(friendPosition), actionBarHeight)

  }

  def startCall(friendPosition: Int): Unit = {

    val friend  = mFriends_Recycler_Adapter.getItem(friendPosition)

    val bundle = new Bundle
    bundle.putString("contactName", friend.getUserName)
    bundle.putInt("contactColorPrimary", friend.getColor)
    bundle.putInt("contactPhotoReference", friend.getPhotoReference)


    val newIntent: Intent = new Intent(getActivity, classOf[CallActivity])
    newIntent.putExtras(bundle)
    getActivity.startActivity(newIntent)

  }

}
