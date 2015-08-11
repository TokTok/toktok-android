package im.tox.toktok.app.NewMessageActivity


import android.support.v7.widget.RecyclerView
import android.util.{Log, SparseBooleanArray}
import android.view.animation.AlphaAnimation
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.Filter.FilterResults
import android.widget.{Filterable, Filter, RelativeLayout, TextView}
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer


class NewMessageRecyclerAdapter(list: ListBuffer[Friend], clickListener: FriendAddOnClick) extends RecyclerView.Adapter[NewMessageRecyclerViewHolder] with Filterable{

  private val selectedItems: SparseBooleanArray = new SparseBooleanArray()
  var friends: ListBuffer[Friend] = list
  private val selectedContacts = new ListBuffer[Friend]
  var savedContacts = list.clone()
  private var listener = clickListener

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewMessageRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.new_message_item, viewGroup, false)
    return new NewMessageRecyclerViewHolder(itemView, listener)
  }

  def onBindViewHolder(viewHolder: NewMessageRecyclerViewHolder, position: Int) = {
    val item: Friend = friends(position)
    viewHolder.mUserName.setText(item.getUserName())
    viewHolder.mUserImage.setImageResource(item.getPhotoReference())

    if (selectedItems.get(item.getID(), false) == true) {
      val animation = new AlphaAnimation(1, 0.3F)
      animation.setDuration(0)
      animation.setFillAfter(true)
      viewHolder.mBase.startAnimation(animation)

    }

    else {
      val animation = new AlphaAnimation(0.3F, 1)
      animation.setDuration(0)
      animation.setFillAfter(true)
      viewHolder.mBase.startAnimation(animation)
    }

  }

  def getItemCount(): Int = {
    return friends.length
  }

  def getItem(i: Int): Friend = {
    return friends(i)
  }

  def getFilter(): Filter ={
    return new FriendFilter(this, savedContacts)
  }

  def setlistener(click: FriendAddOnClick): Unit = {
    listener = click
  }

  def selectItem(position: Int): Unit = {

    if (selectedItems.get(friends(position).getID(), false)) {
      selectedItems.delete(friends(position).getID())
      selectedContacts -= friends(position)
    }
    else {
      selectedItems.put(friends(position).getID(), true)
      selectedContacts += friends(position)
    }

    notifyItemChanged(position)

  }

  def countSelected(): Int = {
    return selectedItems.size()
  }

  def getFirstSelected(): Friend = {
    return selectedContacts(0)
  }

  def clearSelectedList(): Unit = {

    selectedItems.clear()
    selectedContacts.clear()

    notifyDataSetChanged()

  }

  def getSelectedFriends(): ListBuffer[Friend] = {
    return selectedContacts
  }


}

final class NewMessageRecyclerViewHolder(itemView: View, clickListener: FriendAddOnClick) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  itemView.setOnClickListener(this)
  var mUserName: TextView = itemView.findViewById(R.id.new_message_item_name).asInstanceOf[TextView]
  var mUserImage: CircleImageView = itemView.findViewById(R.id.new_message_item_img).asInstanceOf[CircleImageView]
  var mBase = itemView.findViewById(R.id.new_message_item).asInstanceOf[RelativeLayout]
  var listener = clickListener
  itemView.setTag(this)

  def onClick(v: View): Unit = {

    listener.onClickListener(itemView.getTag.asInstanceOf[NewMessageRecyclerViewHolder].getLayoutPosition)

  }

}

trait FriendAddOnClick {
  def onClickListener(position: Int)
}

class FriendFilter(adapter: NewMessageRecyclerAdapter, friendsList: ListBuffer[Friend]) extends Filter {

  private val original = friendsList
  private var filteredResults = new ListBuffer[Friend]
  private val recyclerAdapter = adapter

  override protected def performFiltering(constraint: CharSequence): FilterResults = {

    filteredResults.clear()
    val results = new FilterResults

    if (constraint.length() == 0) {
      filteredResults = original.clone
    }
    else {
      val trimmedString = constraint.toString.toLowerCase.trim

      for (a <- original) {
        if (a.getUserName().toLowerCase.trim.contains(trimmedString)) {
          filteredResults += a
        }
      }
    }

    results.values = filteredResults
    results.count = filteredResults.size

    return results

  }

  override protected def publishResults(constraint: CharSequence, results: FilterResults): Unit = {
    recyclerAdapter.friends.clear
    recyclerAdapter.friends = results.values.asInstanceOf[ListBuffer[Friend]]
    recyclerAdapter.notifyDataSetChanged()
  }

}