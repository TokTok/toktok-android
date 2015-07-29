package im.tox.toktok.app.MainActivity.MainFriendsFragment

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v4.app.{Fragment}
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.ViewGroup.LayoutParams
import android.view.{WindowManager, LayoutInflater, View, ViewGroup}
import android.widget.FrameLayout
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer

class FriendsFragment extends Fragment with FriendPhotoOnClick{

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.fragment_home_friends, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

    //Recycler View

    val mFriends_Recycler: RecyclerView = view.findViewById(R.id.home_friends_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
    mFriends_Recycler.setLayoutManager(mLayoutManager)

    val a = ListBuffer(Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john)

    val mFriends_Recycler_Adapter: FriendsRecyclerHeaderAdapter = new FriendsRecyclerHeaderAdapter(a,this)

    mFriends_Recycler.setAdapter(mFriends_Recycler_Adapter)
    mFriends_Recycler.setHasFixedSize(true)
    mFriends_Recycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))

    return view
  }


  def startFragment(): Unit ={

    val a : FrameLayout = getActivity.findViewById(R.id.home_friends_fragment).asInstanceOf[FrameLayout]

    val b = getActivity.getLayoutInflater.inflate(R.layout.activity_contacts,null).asInstanceOf[SlideInContactsLayout]

    val c = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, PixelFormat.TRANSLUCENT);

    val w = getActivity.getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]

    w.addView(b,c)

    b.start()








  }

}
