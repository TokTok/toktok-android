package im.tox.toktok.app.NewMessageActivity

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import im.tox.toktok.R
import im.tox.toktok.app.MainFriendsFragment.Friend

import scala.collection.mutable.ListBuffer


class NewMessageRecyclerAdapter(list : ListBuffer[Friend]) extends RecyclerView.Adapter[NewMessageRecyclerViewHolder] {

  private val items : ListBuffer[Friend] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewMessageRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.new_message_item, viewGroup, false)
    return new NewMessageRecyclerViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: NewMessageRecyclerViewHolder, position: Int) = {
    val item: Friend = items(position)
    viewHolder.mUserName.setText(item.getUserName())
  }

  def getItemCount(): Int = {
    return items.length
  }

  def getItemPosition(i : Int) : String ={
    return items(i).getUserName()
  }

}