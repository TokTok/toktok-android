package im.tox.toktok.app.MainChatsFragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.{LayoutInflater, View, ViewGroup}
import im.tox.toktok.R
import im.tox.toktok.app.MainFriendsFragment.Friend

import scala.collection.mutable.ListBuffer

class ChatsFragment extends Fragment {

  var mChats_Recycler: RecyclerView = null

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    val view: View = inflater.inflate(R.layout.home_chats, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

    // Recycler View

    mChats_Recycler = view.findViewById(R.id.home_chats_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mChats_Recycler.setLayoutManager(mLayoutManager)
    var a = ListBuffer[ChatsMessageObject]()

    a += new ChatsMessageObject(new Friend("Lorem Ipsum", "Trying to TokTok", 0, Color.parseColor("#DC4254"), Color.parseColor("#7D2530"), R.drawable.lorem), "Hello, how are you?")
    a += new ChatsMessageObject(new Friend("John Doe", "Up!", 0, Color.parseColor("#009688"), Color.parseColor("#00796B"), R.drawable.john), "Hello, how are you?")

    var friends = ListBuffer[Friend]()

    friends += new Friend("Lorem Ipsum", "Trying to TokTok", 0, Color.parseColor("#DC4254"), Color.parseColor("#7D2530"), R.drawable.lorem)
    friends += new Friend("John Doe", "Up!", 0, Color.parseColor("#009688"), Color.parseColor("#00796B"), R.drawable.john)

    a += new ChatsMessageObject(new Group("The Amazing Group", friends, Color.parseColor("#9B9B9B"), Color.parseColor("#5A5A5A")), "Let's Go!")


    val mChats_Recycler_Adapter: ChatsRecyclerAdapter = new ChatsRecyclerAdapter(a)
    mChats_Recycler.setAdapter(mChats_Recycler_Adapter)

    return view

  }

}
