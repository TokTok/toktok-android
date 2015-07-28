package im.tox.toktok.app.MainActivity.MainFriendsFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.{LayoutInflater, View, ViewGroup}
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer

class FriendsFragment extends Fragment {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.fragment_home_friends, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

    //Recycler View

    val mFriends_Recycler: RecyclerView = view.findViewById(R.id.home_friends_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
    mFriends_Recycler.setLayoutManager(mLayoutManager)

    val a = ListBuffer(Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john, Friend.bart, Friend.lorem, Friend.jane, Friend.john)

    val mFriends_Recycler_Adapter: FriendsRecyclerHeaderAdapter = new FriendsRecyclerHeaderAdapter(a)

    mFriends_Recycler.setAdapter(mFriends_Recycler_Adapter)
    mFriends_Recycler.setHasFixedSize(true)
    mFriends_Recycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))

    return view
  }

}
