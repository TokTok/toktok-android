package im.tox.toktok.app.ProfileActivity

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class RejectedCallAdapter(list: ListBuffer[String]) extends RecyclerView.Adapter[RejectedCallViewHolder] {

  private val items: ListBuffer[String] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RejectedCallViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.rejected_call_item, viewGroup, false)
    return new RejectedCallViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: RejectedCallViewHolder, position: Int) = {
    val item: String = items(position)
    viewHolder.mMessage.setText(item)
  }

  def getItemCount(): Int = {
    return items.length
  }

}


final class RejectedCallViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  val mMessage = itemView.findViewById(R.id.reject_item_message).asInstanceOf[TextView]

}