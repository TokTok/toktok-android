package im.tox.toktok.app.ProfileActivity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.Callback
import android.util.Log
import android.view.View.OnTouchListener
import android.view.{MotionEvent, LayoutInflater, View, ViewGroup}
import android.widget.TextView
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class RejectedCallAdapter(list: ListBuffer[String],dragStart: DragStart) extends RecyclerView.Adapter[RejectedCallViewHolder] with DragInterface {

  private val items: ListBuffer[String] = list

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RejectedCallViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.rejected_call_item, viewGroup, false)
    return new RejectedCallViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: RejectedCallViewHolder, position: Int) = {
    val item: String = items(position)
    viewHolder.mMessage.setText(item)

    viewHolder.itemView.setOnTouchListener(new OnTouchListener {
      override def onTouch(v: View, event: MotionEvent): Boolean = {

        if(event.getAction == MotionEvent.ACTION_DOWN){
          dragStart.onDragStart(viewHolder)

        }

        return false
      }
    })

  }

  def getItemCount(): Int = {
    return items.length
  }

  def onItemMove(originalPosition: Int, newPosition: Int) {
    Log.d("asd","asd")
    val originalItem = items(originalPosition)
    items(originalPosition) = items(newPosition)
    items(newPosition) = originalItem
    notifyItemMoved(originalPosition, newPosition)

  }

}


final class RejectedCallViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView){

  val mMessage = itemView.findViewById(R.id.reject_item_message).asInstanceOf[TextView]

}

class DragHelperCallback(adapter: RejectedCallAdapter) extends ItemTouchHelper.Callback {

  override def isLongPressDragEnabled(): Boolean = {
    return true
  }

  override def isItemViewSwipeEnabled(): Boolean = {
    return false
  }

  override def getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int = {

    val dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    return Callback.makeMovementFlags(dragFlags, 0)

  }

  override def onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = {
    Log.d("asd","asdasd")
    adapter.onItemMove(viewHolder.getAdapterPosition, target.getAdapterPosition)
    return true
  }

  def onSwiped(viewHolder: ViewHolder, direction: Int): Unit ={
  }


}

trait DragInterface{
  def onItemMove(originalPosition: Int, newPosition: Int)
}

trait DragStart{
  def onDragStart(viewHolder : ViewHolder)
}