package im.tox.toktok.app.SimpleDialogs

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{ImageView, TextView}
import im.tox.toktok.R
import im.tox.toktok.app.File

import scala.collection.mutable.ListBuffer


class FileSendActivityAdapter(list: ListBuffer[File]) extends RecyclerView.Adapter[FileSendActivityViewHolder] {

  val items: ListBuffer[File] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FileSendActivityViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.files_send_item, viewGroup, false)
    return new FileSendActivityViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: FileSendActivityViewHolder, position: Int) = {
    val item: File = items(position)
    viewHolder.mFileDate.setText(item.getDate())
    viewHolder.mFileName.setText(item.getName())
    viewHolder.mIcon.setImageResource(R.drawable.files_movie)

  }

  def getItemCount(): Int = {
    return items.length
  }

}

final class FileSendActivityViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  var mIcon: ImageView = itemView.findViewById(R.id.files_send_item_icon).asInstanceOf[ImageView]
  var mFileName: TextView = itemView.findViewById(R.id.files_send_item_name).asInstanceOf[TextView]
  var mFileDate: TextView = itemView.findViewById(R.id.files_send_item_date).asInstanceOf[TextView]

}