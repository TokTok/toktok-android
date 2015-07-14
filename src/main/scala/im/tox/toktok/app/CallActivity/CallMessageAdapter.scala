package im.tox.toktok.app.CallActivity

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import im.tox.toktok.R
import scala.collection.mutable.ListBuffer

class CallMessageAdapter (list: ListBuffer[String]) extends RecyclerView.Adapter[CallMessageViewHolder] {

  val items: ListBuffer[String] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CallMessageViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.call_item, viewGroup, false)
    return new CallMessageViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: CallMessageViewHolder, position: Int) = {
    val item: String = items(position)
    viewHolder.mMessage.setText(item)

  }

  def getItemCount(): Int = {
    return items.length
  }

}

final class CallMessageViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  var mMessage : TextView = itemView.findViewById(R.id.call_item_message).asInstanceOf[TextView]

}