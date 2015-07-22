package im.tox.toktok.app.MainActivity.MainChatsFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.{LayoutInflater, View, ViewGroup}
import im.tox.toktok.R
import im.tox.toktok.app.ChatsMessageObject

import scala.collection.mutable.ListBuffer

class ChatsFragment extends Fragment {

  var mChats_Recycler: RecyclerView = null

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    val view: View = inflater.inflate(R.layout.fragment_home_chats, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

    // Recycler View

    mChats_Recycler = view.findViewById(R.id.home_chats_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mChats_Recycler.setLayoutManager(mLayoutManager)

    val mChats_Recycler_Adapter: ChatsRecyclerAdapter = new ChatsRecyclerAdapter(ListBuffer(ChatsMessageObject.loremMessage, ChatsMessageObject.johnMessage, ChatsMessageObject.groupMessage))

    mChats_Recycler.setAdapter(mChats_Recycler_Adapter)

    return view

  }

}
