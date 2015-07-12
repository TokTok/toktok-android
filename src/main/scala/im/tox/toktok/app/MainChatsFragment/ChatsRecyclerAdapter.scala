package im.tox.toktok.app.MainChatsFragment

import android.content.{Intent, Context}
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{RelativeLayout, TextView}
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R
import im.tox.toktok.app.MainFriendsFragment.Friend
import im.tox.toktok.app.MessageActivity.MessageActivity

import scala.collection.mutable.ListBuffer

class ChatsRecyclerAdapter(list: ListBuffer[ChatsMessageObject]) extends RecyclerView.Adapter[RecyclerView.ViewHolder] {

  private val items: ListBuffer[ChatsMessageObject] = list

  override def getItemViewType(position: Int): Int = {
    return items(position).getType()
  }

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder = {

    if (viewType == 0) {

      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.home_chats_item_user, viewGroup, false)
      return new ChatsRecyclerViewHolderUser(itemView, items)

    }
    else {

      val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.home_chats_item_group, viewGroup, false)
      return new ChatsRecyclerViewHolderGroup(itemView, items)

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

    }
    else {

      val view: ChatsRecyclerViewHolderGroup = viewHolder.asInstanceOf[ChatsRecyclerViewHolderGroup]

      view.mUserName.setText(item.getGroup().getGroupName())
      view.mLastMessage.setText(item.getLastMessage())
      view.mColor.setBackgroundColor(item.getPrimaryColor())

    }

  }

  def getItemCount(): Int = {
    return items.length
  }

}

final class ChatsRecyclerViewHolderUser(itemView: View, list: ListBuffer[ChatsMessageObject]) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  var context: Context = itemView.getContext
  itemView.setOnClickListener(this)

  var mUserName: TextView = itemView.findViewById(R.id.home_item_name).asInstanceOf[TextView]
  var mUserStatus: TextView = itemView.findViewById(R.id.home_item_status).asInstanceOf[TextView]
  var mLastMessage: TextView = itemView.findViewById(R.id.home_item_last_message).asInstanceOf[TextView]
  var mUserImage: CircularImageView = itemView.findViewById(R.id.home_item_img).asInstanceOf[CircularImageView]
  var mColor: RelativeLayout = itemView.findViewById(R.id.home_item_color).asInstanceOf[RelativeLayout]

  def onClick(view: View) = {

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

final class ChatsRecyclerViewHolderGroup(itemView: View, list: ListBuffer[ChatsMessageObject]) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  var context: Context = itemView.getContext
  itemView.setOnClickListener(this)

  var mUserName: TextView = itemView.findViewById(R.id.home_item_name).asInstanceOf[TextView]
  var mLastMessage: TextView = itemView.findViewById(R.id.home_item_last_message).asInstanceOf[TextView]
  var mColor: RelativeLayout = itemView.findViewById(R.id.home_item_color).asInstanceOf[RelativeLayout]

  def onClick(view: View) = {

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
