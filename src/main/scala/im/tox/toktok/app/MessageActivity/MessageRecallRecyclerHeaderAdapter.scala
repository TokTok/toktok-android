package im.tox.toktok.app.MessageActivity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.{LayoutInflater, View, ViewGroup}
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class MessageRecallRecyclerHeaderAdapter(list : ListBuffer[Message]) extends MessageRecallRecyclerAdapter(list) with StickyRecyclerHeadersAdapter[RecyclerView.ViewHolder]{

  def getHeaderId(position : Int) : Long = {
    return getItemPosition(position).getMsgDetails().charAt(0);
  }

  def onCreateHeaderViewHolder( parent : ViewGroup) :RecyclerView.ViewHolder = {
    val view : View = LayoutInflater.from(parent.getContext).inflate(R.layout.recall_header,parent,false)
    return new ViewHolder(view) {}
  }

  def onBindHeaderViewHolder( holder : RecyclerView.ViewHolder, position : Int) = {
    //holder.itemView.findViewById(R.id.header).asInstanceOf[TextView].setText(String.valueOf(getItemPosition(position).charAt(0)))
  }

}