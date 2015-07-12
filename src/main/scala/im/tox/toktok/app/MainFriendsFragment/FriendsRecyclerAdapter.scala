package im.tox.toktok.app.MainFriendsFragment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.{LayoutInflater, View, ViewGroup}
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class FriendsRecyclerAdapter(list: ListBuffer[Friend]) extends RecyclerView.Adapter[FriendsRecyclerViewHolder] {

  private val items: ListBuffer[Friend] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FriendsRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.home_friends_item, viewGroup, false)
    return new FriendsRecyclerViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: FriendsRecyclerViewHolder, position: Int) = {
    val item: Friend = items(position)
    viewHolder.mUserName.setText(item.getUserName())
    viewHolder.mUserImage.setImageResource(item.getPhotoReference())
  }

  def getItemCount(): Int = {
    return items.length
  }

  def getItemPosition(i : Int) : String ={
    return items(i).getUserName()
  }

}
