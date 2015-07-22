package im.tox.toktok.app.SimpleDialogs

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import im.tox.toktok.R


class SimpleColorDialogDesignAdapter(list: List[String]) extends RecyclerView.Adapter[SimpleColorDialogDesignViewHolder] {

  var items = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SimpleColorDialogDesignViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.simple_color_dialog_item, viewGroup, false)
    itemView.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        Log.d("asd", "adsd")
        v.setZ(1000.0f)
      }
    })
    return new SimpleColorDialogDesignViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: SimpleColorDialogDesignViewHolder, position: Int) = {
    val item: String = items(position)
    Log.d("asdasd", item + "")
    viewHolder.mColor.setBackgroundColor(Color.parseColor(item))
  }

  def getItemCount(): Int = {
    return items.length
  }
}

final class SimpleColorDialogDesignViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) with OnClickListener {

  var mColor: View = itemView.findViewById(R.id.simple_color_dialog_item)

  def onClick(v: View) = {
    Log.d("asd", "adsd")
  }


}