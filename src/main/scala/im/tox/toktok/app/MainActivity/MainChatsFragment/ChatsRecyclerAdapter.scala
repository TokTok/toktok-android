package im.tox.toktok.app.MainActivity.MainChatsFragment

import android.content.{Context, Intent}
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.{Log, SparseBooleanArray}
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R
import im.tox.toktok.app.ChatsMessageObject
import im.tox.toktok.app.MessageActivity.MessageActivity

import scala.collection.mutable.ListBuffer

class ChatsRecyclerAdapter(list: ListBuffer[ChatsMessageObject], chatItemOnLongClick: ChatItemClick) extends RecyclerView.Adapter[RecyclerView.ViewHolder] {

  private val items: ListBuffer[ChatsMessageObject] = list
  private val selectedItems: SparseBooleanArray = new SparseBooleanArray()

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder = {

    if (viewType == 0) {

      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.fragment_home_chats_item_user, viewGroup, false)
      return new ChatsRecyclerViewHolderUser(itemView, items, chatItemOnLongClick)

    }
    else {

      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.fragment_home_chats_item_group, viewGroup, false)
      return new ChatsRecyclerViewHolderGroup(itemView, items, chatItemOnLongClick)

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

      if (item.isActive()) {
        view.mSelectedBackground.setVisibility(View.VISIBLE)
      }

    }
    else {

      val view: ChatsRecyclerViewHolderGroup = viewHolder.asInstanceOf[ChatsRecyclerViewHolderGroup]

      view.mUserName.setText(item.getGroup().getGroupName())
      view.mLastMessage.setText(item.getLastMessage())
      view.mColor.setBackgroundColor(item.getPrimaryColor())

      if (item.isActive()) {
        view.mSelectedBackground.setVisibility(View.VISIBLE)
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
      selectedItems.put(i, true)
      items(i).setActive(true)
      Log.d("asd", "toggleTrue" + i)
    }
    else {
      selectedItems.delete(i)
      items(i).setActive(false)
      Log.d("asd", "togglefalse")
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

  def getSelectedItems(): Array[Int] = {

    val selectedList = new Array[Int](selectedItems.size())

    var i = 0

    for (i <- 0 to selectedItems.size()) {

      selectedList(0) = selectedItems.keyAt(i)

    }

    return selectedList

  }

}

class ChatsRecyclerViewHolderUser(itemView: View, list: ListBuffer[ChatsMessageObject], clickListener: ChatItemClick) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener with View.OnLongClickListener {

  var context: Context = itemView.getContext
  itemView.setOnClickListener(this)
  itemView.setOnLongClickListener(this)

  var mSelectedBackground = itemView.findViewById(R.id.home_item_selected)
  var mUserName: TextView = itemView.findViewById(R.id.home_item_name).asInstanceOf[TextView]
  var mUserStatus: TextView = itemView.findViewById(R.id.home_item_status).asInstanceOf[TextView]
  var mLastMessage: TextView = itemView.findViewById(R.id.home_item_last_message).asInstanceOf[TextView]
  var mUserImage: CircularImageView = itemView.findViewById(R.id.home_item_img).asInstanceOf[CircularImageView]
  var mColor: View = itemView.findViewById(R.id.home_item_color)

  def onClick(view: View) = {

    if (!clickListener.onClick(getLayoutPosition)) {

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


    return clickListener.onLongClick(getLayoutPosition)

  }

}

class ChatsRecyclerViewHolderGroup(itemView: View, list: ListBuffer[ChatsMessageObject], clickListener: ChatItemClick) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener with View.OnLongClickListener {

  var context: Context = itemView.getContext
  itemView.setOnClickListener(this)
  itemView.setOnLongClickListener(this)

  var mSelectedBackground = itemView.findViewById(R.id.home_item_selected)
  var mUserName: TextView = itemView.findViewById(R.id.home_item_name).asInstanceOf[TextView]
  var mLastMessage: TextView = itemView.findViewById(R.id.home_item_last_message).asInstanceOf[TextView]
  var mColor: View = itemView.findViewById(R.id.home_item_color)

  def onClick(view: View) = {

    if (!clickListener.onClick(getLayoutPosition)) {

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

    return clickListener.onLongClick(getLayoutPosition)

  }

}


trait ChatItemClick {
  def onLongClick(i: Int): Boolean

  def onClick(i: Int): Boolean
}
