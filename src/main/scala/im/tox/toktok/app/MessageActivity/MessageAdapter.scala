package im.tox.toktok.app.MessageActivity

import android.support.v7.widget.{CardView, RecyclerView}
import android.util.{Log, SparseBooleanArray}
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{RelativeLayout, TextView}
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.Message

import scala.collection.mutable.ListBuffer

class MessageAdapter(list: ListBuffer[Message], messageClick: MessageClick, messageActionMode: MessageActionMode) extends RecyclerView.Adapter[RecyclerView.ViewHolder] {

  private val items: ListBuffer[Message] = list
  private val selectedItems: SparseBooleanArray = new SparseBooleanArray()
  private var actionModeActive = false;


  override def getItemViewType(position: Int): Int = {
    return items(position).getType()
  }

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder = {

    var recyclerViewItem: RecyclerView.ViewHolder = null;

    if (viewType == 1) {
      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.message_item_user_simple, viewGroup, false)
      recyclerViewItem = new MessageViewHolderSimple(itemView, messageActionMode)
    }

    else if (viewType == 2) {
      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.message_item_friend_simple, viewGroup, false)
      recyclerViewItem = new MessageViewHolderSimple(itemView, messageActionMode)
    }

    else if (viewType == 3) {
      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.message_item_action, viewGroup, false)
      recyclerViewItem = new MessageViewHolderAction(itemView, messageActionMode)
    }



    return recyclerViewItem
  }

  def toggleSelection(i: Int): Unit = {

    if (selectedItems.get(i, false)) {
      selectedItems.delete(i)
    }
    else {
      selectedItems.put(i, true)
    }
    notifyItemChanged(i)

  }

  def getSelectedItemCount(): Int = {
    return selectedItems.size()
  }

  def isSelected(position: Int): Boolean = {
    return selectedItems.get(position, false)
  }

  def onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) = {
    val item: Message = items(position)

    if (getItemViewType(position) == 1 ) {

      val view: MessageViewHolderSimple = viewHolder.asInstanceOf[MessageViewHolderSimple]

      view.mUserText.setText(item.getMsgContent())
      view.mUserDetais.setText(item.getMsgDetails())
      view.mUserImg.setImageResource(item.getImageSrc())
      view.mUserImg.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          messageClick.onImgClick()
        }
      })

      if(actionModeActive && !isSelected(position) ){
        view.mBase.setAlpha(0.5f)
      }
      else{
        view.mBase.setAlpha(1)
      }

    }

    else if (getItemViewType(position) == 2) {

      val view: MessageViewHolderSimple = viewHolder.asInstanceOf[MessageViewHolderSimple]

      view.mUserText.setText(item.getMsgContent())
      view.mUserDetais.setText(item.getMsgDetails())
      view.mUserImg.setImageResource(item.getImageSrc())

      if(actionModeActive && !isSelected(position) ){
        view.mBase.setAlpha(0.5f)
      }
      else{
        view.mBase.setAlpha(1)
      }

    }



    else if (getItemViewType(position) == 3) {

      val view: MessageViewHolderAction = viewHolder.asInstanceOf[MessageViewHolderAction]
      view.mUserText.setText(item.getMsgContent())
      view.mUserImg.setImageResource(item.getImageSrc())
      view.mUserImg.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          messageClick.onImgClick()
        }
      })

      if(actionModeActive && !isSelected(position) ){
        view.mBase.setAlpha(0.5f)
      }
      else{
        view.mBase.setAlpha(1)
      }

    }

  }

  def getItemCount(): Int = {
    return items.length
  }


  def addItem(newMsg: Message): Unit = {
    items.insert(0, newMsg)
    notifyItemInserted(0)
  }

  def setActionModeActive(state : Boolean): Unit ={
    actionModeActive = state
    Log.d("asdasd",actionModeActive+"")
  }

  def clearSelections(): Unit = {
    selectedItems.clear()
    notifyDataSetChanged()
  }

  def deleteSelected(): Unit ={

    var a : Int = 0

    for ( a <- (items.size-1) to 0 by -1){
      if(selectedItems.get(a)){
        items.remove(a)
        notifyItemRemoved(a)
      }
    }

    selectedItems.clear()

  }

}

class MessageViewHolderSimple(itemView: View, messageActionMode: MessageActionMode) extends RecyclerView.ViewHolder(itemView) with View.OnLongClickListener with View.OnClickListener {

  var mBase : CardView = itemView.findViewById(R.id.message_item_base).asInstanceOf[CardView]
  var mUserText: TextView = itemView.findViewById(R.id.message_item_text).asInstanceOf[TextView]
  var mUserDetais: TextView = itemView.findViewById(R.id.message_item_details).asInstanceOf[TextView]
  var mUserImg: CircleImageView = itemView.findViewById(R.id.message_item_img).asInstanceOf[CircleImageView]
  itemView.setOnLongClickListener(this)
  itemView.setOnClickListener(this)

  def onLongClick(v: View): Boolean = {
    return messageActionMode.onLongClick(getLayoutPosition);
  }

  def onClick(v: View): Unit = {
    messageActionMode.onClick(getLayoutPosition)
  }

}


class MessageViewHolderAction(itemView: View, messageActionMode: MessageActionMode) extends RecyclerView.ViewHolder(itemView) with View.OnLongClickListener with View.OnClickListener {

  var mBase : CardView = itemView.findViewById(R.id.message_item_action_cardview).asInstanceOf[CardView]
  var mUserText: TextView = itemView.findViewById(R.id.message_item_text).asInstanceOf[TextView]
  var mUserImg: CircleImageView = itemView.findViewById(R.id.message_item_img).asInstanceOf[CircleImageView]
  itemView.setOnLongClickListener(this)
  itemView.setOnClickListener(this)

  def onLongClick(v: View): Boolean = {
    return messageActionMode.onLongClick(getLayoutPosition);
  }

  def onClick(v: View): Unit = {
    messageActionMode.onClick(getLayoutPosition)
  }

}

trait MessageClick {
  def onImgClick()
}

trait MessageActionMode {
  def onLongClick(i: Int): Boolean
  def onClick(i: Int)
}