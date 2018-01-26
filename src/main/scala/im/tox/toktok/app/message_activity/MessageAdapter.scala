package im.tox.toktok.app.message_activity

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.{ LayoutInflater, View, ViewGroup }
import android.widget.{ RelativeLayout, TextView }
import com.typesafe.scalalogging.Logger
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.domain.{ Message, MessageType }
import im.tox.toktok.app.message_activity.MessageAdapter.inflate
import im.tox.toktok.{ TR, TypedLayout }
import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer

object MessageAdapter {
  private def inflate[A <: View](layout: TypedLayout[A], viewGroup: ViewGroup): A = {
    LayoutInflater.from(viewGroup.getContext).inflate(layout, viewGroup, false)
  }
}

final class MessageAdapter(
    messageClick: MessageClick,
    messageActionMode: MessageActionMode
) extends RecyclerView.Adapter[MessageViewHolder] {

  val logger = Logger(LoggerFactory.getLogger(getClass))

  private val messages = new ListBuffer[Message]
  private val selectedItems = new SparseBooleanArray()
  private var actionModeActive = false

  override def getItemViewType(position: Int): Int = {
    messages(position).msgType.viewType
  }

  override def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessageViewHolder = {
    viewType match {
      case MessageType.Delivered.viewType =>
        val itemView = inflate(TR.layout.message_item_user_simple, viewGroup)
        new MessageViewHolderDetailed(itemView, messageActionMode)
      case MessageType.Received.viewType =>
        val itemView = inflate(TR.layout.message_item_friend_simple, viewGroup)
        new MessageViewHolderDetailed(itemView, messageActionMode)
      case MessageType.Action.viewType =>
        val itemView = inflate(TR.layout.message_item_action, viewGroup)
        new MessageViewHolder(itemView, messageActionMode, itemView.findView(TR.message_item_action))
    }
  }

  def toggleSelection(i: Int): Unit = {
    if (selectedItems.get(i, false)) {
      selectedItems.delete(i)
    } else {
      selectedItems.put(i, true)
    }
    notifyItemChanged(i)
  }

  def getSelectedItemCount: Int = {
    selectedItems.size()
  }

  def isSelected(position: Int): Boolean = {
    selectedItems.get(position, false)
  }

  override def onBindViewHolder(viewHolder: MessageViewHolder, position: Int): Unit = {
    val message = messages(position)

    viewHolder.mUserText.setText(message.msgContent)
    viewHolder.mUserImg.setImageResource(message.imageSrc)

    if (actionModeActive && !isSelected(position)) {
      viewHolder.base.setAlpha(0.5f)
    } else {
      viewHolder.base.setAlpha(1)
    }

    getItemViewType(position) match {
      case MessageType.Delivered.viewType =>
        val view = viewHolder.asInstanceOf[MessageViewHolderDetailed]
        view.mUserDetails.setText(message.msgDetails)

      case MessageType.Received.viewType =>
        viewHolder.mUserImg.setOnClickListener(new View.OnClickListener {
          override def onClick(v: View): Unit = {
            messageClick.onImgClick()
          }
        })

        val view = viewHolder.asInstanceOf[MessageViewHolderDetailed]
        view.mUserDetails.setText(message.msgDetails)

      case MessageType.Action.viewType =>
        viewHolder.mUserImg.setOnClickListener(new View.OnClickListener {
          override def onClick(v: View): Unit = {
            messageClick.onImgClick()
          }
        })
    }
  }

  def getItemCount: Int = {
    messages.length
  }

  def addItem(newMsg: Message): Unit = {
    messages.insert(0, newMsg)
    notifyItemInserted(0)
  }

  def setActionModeActive(state: Boolean): Unit = {
    actionModeActive = state
    logger.debug(actionModeActive.toString)
  }

  def clearSelections(): Unit = {
    selectedItems.clear()
    notifyDataSetChanged()
  }

  def deleteSelected(): Unit = {
    for (a <- (messages.size - 1) to 0 by -1) {
      if (selectedItems.get(a)) {
        messages.remove(a)
        notifyItemRemoved(a)
      }
    }

    selectedItems.clear()
  }

}

sealed class MessageViewHolder(
    itemView: RelativeLayout,
    messageActionMode: MessageActionMode,
    final val base: View
) extends RecyclerView.ViewHolder(itemView)
  with View.OnLongClickListener
  with View.OnClickListener {

  final val mUserText: TextView = itemView.findView(TR.message_item_text)
  final val mUserImg: CircleImageView = itemView.findView(TR.message_item_img)

  itemView.setOnLongClickListener(this)
  itemView.setOnClickListener(this)

  def onLongClick(v: View): Boolean = {
    messageActionMode.onLongClick(getLayoutPosition)
  }

  def onClick(v: View): Unit = {
    messageActionMode.onClick(getLayoutPosition)
  }

}

final class MessageViewHolderDetailed(
    itemView: RelativeLayout,
    messageActionMode: MessageActionMode
) extends MessageViewHolder(
  itemView,
  messageActionMode,
  itemView.findView(TR.message_item_base)
) {
  val mUserDetails: TextView = itemView.findView(TR.message_item_details)
}

trait MessageClick {
  def onImgClick(): Unit
}

trait MessageActionMode {
  def onLongClick(i: Int): Boolean
  def onClick(i: Int): Unit
}
