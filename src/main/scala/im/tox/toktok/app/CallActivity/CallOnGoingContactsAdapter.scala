package im.tox.toktok.app.CallActivity

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{TextView, ImageView}
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer


class CallOnGoingContactsAdapter (list: ListBuffer[Friend]) extends RecyclerView.Adapter[CallOnGoingContactsViewHolder] {

  val items: ListBuffer[Friend] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CallOnGoingContactsViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.call_ongoing_contact, viewGroup, false)
    return new CallOnGoingContactsViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: CallOnGoingContactsViewHolder, position: Int) = {
    val item: Friend = items(position)
    viewHolder.mFriendName.setText(item.getUserName())
    viewHolder.mFriendImage.setImageResource(item.getPhotoReference())

  }

  def getItemCount(): Int = {
    return items.length
  }

}

final class CallOnGoingContactsViewHolder (itemView: View) extends RecyclerView.ViewHolder(itemView) {

  val mFriendImage : ImageView = itemView.findViewById(R.id.call_ongoing_contact_img).asInstanceOf[ImageView]
  val mFriendName : TextView = itemView.findViewById(R.id.call_ongoing_contact_name).asInstanceOf[TextView]
  val mFriendCallTime : TextView = itemView.findViewById(R.id.call_ongoing_contact_calltime).asInstanceOf[TextView]

}