package im.tox.toktok.app.MainFriendsFragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.{ LayoutInflater, View, ViewGroup}
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class FriendsFragment extends Fragment {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.home_friends, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

    //Recycler View

    val mFriends_Recycler: RecyclerView = view.findViewById(R.id.home_friends_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
    mFriends_Recycler.setLayoutManager(mLayoutManager)


    var a = ListBuffer[Friend]()

    a+= new Friend("Lorem Ipsum","Trying the TokTok",0,Color.parseColor("#DC4254"),Color.parseColor("#7D2530"),R.drawable.lorem)
    a+= new Friend("John Doe","Up!",0,Color.parseColor("#009688"),Color.parseColor("#00796B"),R.drawable.john)


    val mFriends_Recycler_Adapter: FriendsRecyclerHeaderAdapter = new FriendsRecyclerHeaderAdapter(a)

    mFriends_Recycler.setAdapter(mFriends_Recycler_Adapter)
    mFriends_Recycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))


    return view
  }

}
