package im.tox.toktok.app.NewMessageActivity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import im.tox.toktok.R
import im.tox.toktok.app.MainFriendsFragment.Friend

import scala.collection.mutable.ListBuffer


class NewMessageRecyclerHeaderAdapter(list: ListBuffer[Friend]) extends NewMessageRecyclerAdapter(list) with StickyRecyclerHeadersAdapter[RecyclerView.ViewHolder]{

  def getHeaderId(position : Int) : Long = {
    return getItemPosition(position).charAt(0);
  }

  def onCreateHeaderViewHolder( parent : ViewGroup) :RecyclerView.ViewHolder = {
    val view : View = LayoutInflater.from(parent.getContext).inflate(R.layout.recyclerview_header,parent,false)
    return new ViewHolder(view) {}
  }

  def onBindHeaderViewHolder( holder : RecyclerView.ViewHolder, position : Int) = {
    holder.itemView.findViewById(R.id.recycler_header_text).asInstanceOf[TextView].setText(String.valueOf(getItemPosition(position).charAt(0)))
  }

}
