package im.tox.toktok.app.MainActivity.MainChatsFragment

import android.content.{Context, Intent}
import android.os.Bundle
import android.support.v7.widget.{CardView, RecyclerView}
import android.util.{Log, SparseBooleanArray}
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.ChatsMessageObject
import im.tox.toktok.app.MessageActivity.MessageActivity

import scala.collection.mutable.ListBuffer

class ChatsRecyclerAdapter(list: ListBuffer[ChatsMessageObject], chatItemClick: ChatItemClick) extends RecyclerView.Adapter[RecyclerView.ViewHolder] {

  private val items: ListBuffer[ChatsMessageObject] = list
  private val selectedItems: SparseBooleanArray = new SparseBooleanArray()
  private val chatItemClickListener: ChatItemClick = chatItemClick

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder = {

    if (viewType == 0) {

      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.fragment_home_chats_item_user, viewGroup, false)
      return new ChatsRecyclerViewHolderUser(itemView, items, chatItemClickListener)

    }
    else {

      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.fragment_home_chats_item_group, viewGroup, false)
      return new ChatsRecyclerViewHolderGroup(itemView, items, chatItemClickListener)

    }

  }

  def onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) = {
    val item: ChatsMessageObject = items(position)

    if (getItemViewType(position) == 0) {

      val view: ChatsRecyclerViewHolderUser = viewHolder.asInstanceOf[ChatsRecyclerViewHolderUser]

      view.mUserName.setText(item.getUser().getUserName())
      view.mUserStatus.setText(item.getUser().getUserMessage())
      view.mLastMessage.setText(item.getLastMessage())
      view.mUserImage.setImageResource(item.getUser().getPhotoReference())
      view.mColor.setBackgroundColor(item.getUser().getColor())


      if (isSelected(position)) {
        view.mSelectedBackground.setVisibility(View.VISIBLE);
      }
      else {
        view.mSelectedBackground.setVisibility(View.INVISIBLE);
      }


    }
    else {

      val view: ChatsRecyclerViewHolderGroup = viewHolder.asInstanceOf[ChatsRecyclerViewHolderGroup]

      view.mUserName.setText(item.getGroup().getGroupName())
      view.mLastMessage.setText(item.getLastMessage())
      view.mColor.setBackgroundColor(item.getPrimaryColor())

      if (isSelected(position)) {
        view.mSelectedBackground.setVisibility(View.VISIBLE);
      }
      else {
        view.mSelectedBackground.setVisibility(View.INVISIBLE);
      }

    }


  }

  override def getItemViewType(position: Int): Int = {
    return items(position).getType()
  }

  def getItemCount(): Int = {
    return items.length
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

  def clearSelections(): Unit = {
    selectedItems.clear()
    notifyDataSetChanged()
  }

  def getSelectedItemCount(): Int = {
    return selectedItems.size()
  }

  def isSelected(position: Int): Boolean = {
    return selectedItems.get(position, false)
  }

  def getSelectedItems(): ListBuffer[Int] = {

    val selectedList = ListBuffer[Int](selectedItems.size)

    var i = 0

    for (i <- 0 to selectedItems.size()) {

      selectedList += selectedItems.keyAt(i)

    }

    return selectedList

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

class ChatsRecyclerViewHolderUser(itemView: View, list: ListBuffer[ChatsMessageObject], clickListener: ChatItemClick) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener with View.OnLongClickListener {

  var context: Context = itemView.getContext
  itemView.setOnClickListener(this)
  itemView.setOnLongClickListener(this)

  var mBase = itemView.findViewById(R.id.home_item_view)
  var mSelectedBackground = itemView.findViewById(R.id.home_item_selected)
  var mUserName: TextView = itemView.findViewById(R.id.home_item_name).asInstanceOf[TextView]
  var mUserStatus: TextView = itemView.findViewById(R.id.home_item_status).asInstanceOf[TextView]
  var mLastMessage: TextView = itemView.findViewById(R.id.home_item_last_message).asInstanceOf[TextView]
  var mUserImage: CircleImageView = itemView.findViewById(R.id.home_item_img).asInstanceOf[CircleImageView]
  var mColor: View = itemView.findViewById(R.id.home_item_color)
  var itemClickListener: ChatItemClick = clickListener


  def onClick(view: View) = {

    if (!itemClickListener.onClick(getLayoutPosition)) {

      val item: ChatsMessageObject = list(getLayoutPosition)

      val bundle: Bundle = new Bundle()

      bundle.putInt("messageType", 0)
      bundle.putInt("contactColorPrimary", item.getPrimaryColor())
      bundle.putInt("contactColorStatus", item.getStatusColor())
      bundle.putString("messageTitle", item.getUser().getUserName())
      bundle.putInt("imgResource", item.getUser().getPhotoReference())

      val messageIntent: Intent = new Intent(context, classOf[MessageActivity])
      messageIntent.putExtras(bundle)

      context.startActivity(messageIntent)

    }


  }

  def onLongClick(v: View): Boolean = {


    return itemClickListener.onLongClick(getLayoutPosition)

  }

}

class ChatsRecyclerViewHolderGroup(itemView: View, list: ListBuffer[ChatsMessageObject], clickListener: ChatItemClick) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener with View.OnLongClickListener {

  var context: Context = itemView.getContext
  itemView.setOnClickListener(this)
  itemView.setOnLongClickListener(this)

  var mBase: CardView = itemView.findViewById(R.id.home_item_view).asInstanceOf[CardView]
  var mSelectedBackground = itemView.findViewById(R.id.home_item_selected)
  var mUserName: TextView = itemView.findViewById(R.id.home_item_name).asInstanceOf[TextView]
  var mLastMessage: TextView = itemView.findViewById(R.id.home_item_last_message).asInstanceOf[TextView]
  var mColor: View = itemView.findViewById(R.id.home_item_color)
  var itemClickListener: ChatItemClick = clickListener


  def onClick(view: View) = {

    if (!itemClickListener.onClick(getLayoutPosition)) {

      val item: ChatsMessageObject = list(getLayoutPosition)

      val bundle: Bundle = new Bundle()

      bundle.putInt("messageType", 1)
      bundle.putInt("contactColorPrimary", item.getPrimaryColor())
      bundle.putInt("contactColorStatus", item.getStatusColor())
      bundle.putString("messageTitle", item.getGroup().getGroupName())

      val messageIntent: Intent = new Intent(context, classOf[MessageActivity])
      messageIntent.putExtras(bundle)

      context.startActivity(messageIntent)

    }

  }

  def onLongClick(v: View): Boolean = {

    return itemClickListener.onLongClick(getLayoutPosition)

  }

}


trait ChatItemClick {
  def onLongClick(i: Int): Boolean
  def onClick(i: Int): Boolean
}
