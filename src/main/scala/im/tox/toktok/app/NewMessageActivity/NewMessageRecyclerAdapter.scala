package im.tox.toktok.app.NewMessageActivity

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.animation.AlphaAnimation
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{RelativeLayout, TextView}
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer


class NewMessageRecyclerAdapter(list: ListBuffer[Friend], clickListener: FriendAddOnClick) extends RecyclerView.Adapter[NewMessageRecyclerViewHolder] {

  private val selectedItems: SparseBooleanArray = new SparseBooleanArray()
  private val items: ListBuffer[Friend] = list
  private val selectedContacts = new ListBuffer[Friend]
  private var listener = clickListener

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewMessageRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.new_message_item, viewGroup, false)
    return new NewMessageRecyclerViewHolder(itemView, listener)
  }

  def onBindViewHolder(viewHolder: NewMessageRecyclerViewHolder, position: Int) = {
    val item: Friend = items(position)
    viewHolder.mUserName.setText(item.getUserName())
    viewHolder.mUserImage.setImageResource(item.getPhotoReference())

    if (selectedItems.get(position, false) == true) {
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
    return items.length
  }

  def getItem(i: Int): Friend = {
    return items(i)
  }

  def setlistener(click: FriendAddOnClick): Unit = {
    listener = click
  }

  def selectItem(position: Int): Unit = {

    if (selectedItems.get(position, false)) {
      selectedItems.delete(position)
      selectedContacts -= items(position)
    }
    else {
      selectedItems.put(position, true)
      selectedContacts += items(position)
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

    selectItem(selectedItems.keyAt(0))
    selectedItems.clear()

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