package im.tox.toktok.app.MessageActivity

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import im.tox.toktok.R
import im.tox.toktok.app.Message

import scala.collection.mutable.ListBuffer


class MessageRecallRecyclerAdapter(list: ListBuffer[Message]) extends RecyclerView.Adapter[MessageRecallRecyclerViewHolder] {

  val items: ListBuffer[Message] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessageRecallRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.recall_item, viewGroup, false)
    return new MessageRecallRecyclerViewHolder(itemView)

  }

  def onBindViewHolder(viewHolder: MessageRecallRecyclerViewHolder, position: Int) = {

    val item: Message = items(position)
    val view: MessageRecallRecyclerViewHolder = viewHolder.asInstanceOf[MessageRecallRecyclerViewHolder]

    view.mMessageText.setText(item.getMsgContent())
    view.mMessageDetails.setText(item.getMsgDetails())

  }

  def getItemCount(): Int = {
    return items.length
  }

  def getItemPosition(position: Int): Message = {
    return items(position)
  }

}


final class MessageRecallRecyclerViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  var mMessageText = itemView.findViewById(R.id.message_item_text).asInstanceOf[TextView]
  var mMessageDetails = itemView.findViewById(R.id.message_item_details).asInstanceOf[TextView]


}