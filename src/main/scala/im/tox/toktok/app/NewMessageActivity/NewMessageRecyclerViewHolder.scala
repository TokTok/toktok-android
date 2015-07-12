package im.tox.toktok.app.NewMessageActivity

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import im.tox.toktok.R

final class NewMessageRecyclerViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  itemView.setOnClickListener(this)

  var mUserName: TextView = itemView.findViewById(R.id.newmessage_item_name).asInstanceOf[TextView]

  def onClick(view: View): Unit = {

  }

}