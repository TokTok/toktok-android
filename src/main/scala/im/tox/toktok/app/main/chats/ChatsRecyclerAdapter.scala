package im.tox.toktok.app.main.chats

import android.content.Context
import android.support.v7.widget.{ CardView, RecyclerView }
import android.util.SparseBooleanArray
import android.view.{ LayoutInflater, View, ViewGroup }
import im.tox.toktok.TypedBundleKey._
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.domain.{ ChatMessage, FriendMessage, GroupMessage }
import im.tox.toktok.app.message_activity.MessageActivity
import im.tox.toktok.{ BundleKey, TR }
import org.scaloid.common.SIntent

import scala.collection.mutable.ListBuffer

final class ChatsRecyclerAdapter(
    chatMessages: ListBuffer[ChatMessage],
    chatItemClick: ChatItemClick
) extends RecyclerView.Adapter[RecyclerView.ViewHolder] {

  private val selectedItems = new SparseBooleanArray()

  override def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder = {
    viewType match {
      case 0 =>
        val itemView = LayoutInflater.from(viewGroup.getContext).inflate(
          TR.layout.fragment_home_chats_item_user, viewGroup, false
        )
        new ChatsRecyclerViewHolderUser(itemView, chatMessages, chatItemClick)
      case 1 =>
        val itemView = LayoutInflater.from(viewGroup.getContext).inflate(
          TR.layout.fragment_home_chats_item_group, viewGroup, false
        )
        new ChatsRecyclerViewHolderGroup(itemView, chatMessages, chatItemClick)
    }
  }

  override def onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) = {
    chatMessages(position) match {
      case FriendMessage(friend, lastMessage) =>
        val view = viewHolder.asInstanceOf[ChatsRecyclerViewHolderUser]

        view.mUserName.setText(friend.userName)
        view.mUserStatus.setText(friend.userMessage)
        view.mLastMessage.setText(lastMessage)
        view.mUserImage.setImageResource(friend.photoReference)
        view.mColor.setBackgroundColor(friend.color)

        if (isSelected(position)) {
          view.mSelectedBackground.setVisibility(View.VISIBLE)
        } else {
          view.mSelectedBackground.setVisibility(View.INVISIBLE)
        }

      case GroupMessage(group, lastMessage) =>
        val view = viewHolder.asInstanceOf[ChatsRecyclerViewHolderGroup]

        view.mUserName.setText(group.groupName)
        view.mLastMessage.setText(lastMessage)
        view.mColor.setBackgroundColor(group.primaryColor)

        if (isSelected(position)) {
          view.mSelectedBackground.setVisibility(View.VISIBLE)
        } else {
          view.mSelectedBackground.setVisibility(View.INVISIBLE)
        }

    }
  }

  override def getItemViewType(position: Int): Int = {
    ChatMessage.messageType(chatMessages(position))
  }

  def getItemCount: Int = {
    chatMessages.length
  }

  def toggleSelection(i: Int): Unit = {
    if (selectedItems.get(i, false)) {
      selectedItems.delete(i)
    } else {
      selectedItems.put(i, true)
    }

    notifyItemChanged(i)
  }

  def clearSelections(): Unit = {
    selectedItems.clear()
    notifyDataSetChanged()
  }

  def getSelectedItemCount: Int = {
    selectedItems.size()
  }

  def isSelected(position: Int): Boolean = {
    selectedItems.get(position, false)
  }

  def getSelectedItems: Seq[Int] = {
    (0 to selectedItems.size()).map(selectedItems.keyAt)
  }

  def deleteSelected(): Unit = {
    for (a <- (chatMessages.size - 1) to 0 by -1) {
      if (selectedItems.get(a)) {
        chatMessages.remove(a)
        notifyItemRemoved(a)
      }
    }

    selectedItems.clear()
  }

}

sealed abstract class ChatsRecyclerViewHolder(
    itemView: View,
    chatMessages: Seq[ChatMessage],
    clickListener: ChatItemClick
) extends RecyclerView.ViewHolder(itemView)
  with View.OnClickListener
  with View.OnLongClickListener {

  protected implicit def context: Context = itemView.getContext

  itemView.setOnClickListener(this)
  itemView.setOnLongClickListener(this)

  val mSelectedBackground = itemView.findView(TR.home_item_selected)
  val mUserName = itemView.findView(TR.home_item_name)
  val mLastMessage = itemView.findView(TR.home_item_last_message)
  val mColor = itemView.findView(TR.home_item_color)

  final override def onLongClick(v: View): Boolean = {
    clickListener.onLongClick(getLayoutPosition)
  }

}

final class ChatsRecyclerViewHolderUser(
    itemView: View,
    chatMessages: Seq[ChatMessage],
    clickListener: ChatItemClick
) extends ChatsRecyclerViewHolder(
  itemView,
  chatMessages,
  clickListener
) {

  val mUserStatus = itemView.findView(TR.home_item_status)
  val mUserImage = itemView.findView(TR.home_item_img)

  def onClick(view: View) = {
    if (!clickListener.onClick(getLayoutPosition)) {
      val bundle = chatMessages(getLayoutPosition) match {
        case FriendMessage(friend, lastMessage) =>
          SBundle(
            BundleKey.messageType -> 0,
            BundleKey.contactColorPrimary -> friend.color,
            BundleKey.contactColorStatus -> friend.secondColor,
            BundleKey.messageTitle -> friend.userName,
            BundleKey.imgResource -> friend.photoReference
          )

        case GroupMessage(_, _) =>
          throw new RuntimeException("Got group message but expected friend message")
      }

      context.startActivity(SIntent[MessageActivity].putExtras(bundle))
    }
  }

}

final class ChatsRecyclerViewHolderGroup(
    itemView: CardView,
    chatMessages: Seq[ChatMessage],
    clickListener: ChatItemClick
) extends ChatsRecyclerViewHolder(
  itemView,
  chatMessages,
  clickListener
) {

  override def onClick(view: View) = {
    if (!clickListener.onClick(getLayoutPosition)) {
      val bundle = chatMessages(getLayoutPosition) match {
        case GroupMessage(group, lastMessage) =>
          SBundle(
            BundleKey.messageType -> 1,
            BundleKey.contactColorPrimary -> group.primaryColor,
            BundleKey.contactColorStatus -> group.statusColor,
            BundleKey.messageTitle -> group.groupName
          )

        case FriendMessage(_, _) =>
          throw new RuntimeException("Got friend message but expected group message")
      }

      context.startActivity(SIntent[MessageActivity].putExtras(bundle))
    }
  }

}

trait ChatItemClick {
  def onLongClick(i: Int): Boolean
  def onClick(i: Int): Boolean
}
