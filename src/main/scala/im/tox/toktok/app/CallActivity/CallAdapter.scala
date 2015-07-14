package im.tox.toktok.app.CallActivity

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import im.tox.toktok.R
import scala.collection.mutable.ListBuffer

class CallAdapter (list: ListBuffer[String]) extends RecyclerView.Adapter[CallViewHolder] {

  val items: ListBuffer[String] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CallViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.call_item, viewGroup, false)
    return new CallViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: CallViewHolder, position: Int) = {
    val item: String = items(position)
    viewHolder.mMessage.setText(item)

  }

  def getItemCount(): Int = {
    return items.length
  }

}

final class CallViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  var mMessage : TextView = itemView.findViewById(R.id.call_item_message).asInstanceOf[TextView]

}