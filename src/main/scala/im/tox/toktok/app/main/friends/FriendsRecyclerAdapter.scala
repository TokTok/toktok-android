package im.tox.toktok.app.main.friends

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.{ LayoutInflater, View, ViewGroup }
import android.widget.RelativeLayout
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.domain.Friend
import im.tox.toktok.{ R, TR }
import org.scaloid.common._

abstract class FriendsRecyclerAdapter(
    friends: Seq[Friend],
    expandOnClick: FriendItemClicks
) extends RecyclerView.Adapter[FriendsRecyclerViewHolder] {

  private var expandedItem = -1

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FriendsRecyclerViewHolder = {
    val itemView = LayoutInflater.from(viewGroup.getContext).inflate(TR.layout.fragment_home_friends_item, viewGroup, false)

    new FriendsRecyclerViewHolder(itemView, new FriendItemExpand {
      override def onClick(): Unit = {
        val holder = itemView.getTag.asInstanceOf[ViewHolder]

        if (expandedItem == holder.getLayoutPosition) {
          notifyItemChanged(expandedItem)
          expandedItem = -1
        } else {
          if (expandedItem >= 0) {
            notifyItemChanged(expandedItem)
          }

          expandedItem = holder.getLayoutPosition
          notifyItemChanged(expandedItem)
        }
      }
    })
  }

  def onBindViewHolder(viewHolder: FriendsRecyclerViewHolder, position: Int) = {
    val item = friends(position)
    viewHolder.mUserName.setText(item.userName)
    viewHolder.mUserImage.setImageResource(item.photoReference)
    viewHolder.mUserImage.setClickable(true)
    viewHolder.mUserImage.onClick {
      expandOnClick.startOverLayFriend(position)
    }

    if (position == expandedItem) {
      viewHolder.mExpandedArea.setVisibility(View.VISIBLE)
      viewHolder.mBase.setElevation(10)
      viewHolder.mBase.setBackgroundResource(R.drawable.cardboard_ripple)

      viewHolder.mCallButton.onClick {
        expandOnClick.startCall(position)
      }

      viewHolder.mMessageButton.onClick {
        expandOnClick.startMessage(position)
      }
    } else {
      viewHolder.mExpandedArea.setVisibility(View.GONE)
      viewHolder.mBase.setElevation(0)
      viewHolder.mBase.setBackgroundResource(R.drawable.background_ripple)

      viewHolder.mCallButton.setOnClickListener(null)
      viewHolder.mMessageButton.setOnClickListener(null)
    }

  }

  def getItemCount: Int = {
    friends.length
  }

  def getItemPosition(i: Int): String = {
    friends(i).userName
  }

  def getItem(i: Int): Friend = {
    friends(i)
  }

}

final class FriendsRecyclerViewHolder(
    itemView: RelativeLayout,
    itemOnClick: FriendItemExpand
) extends RecyclerView.ViewHolder(itemView)
  with View.OnClickListener {

  val mBase = itemView.findView(TR.home_friends_base)
  val mUserInfo = itemView.findView(TR.home_item_info)
  val mUserName = itemView.findView(TR.home_friends_name)
  val mUserImage = itemView.findView(TR.home_friends_img)
  val mExpandedArea = itemView.findView(TR.home_friends_expanded_area)
  val mCallButton = itemView.findView(TR.home_friends_call)
  val mMessageButton = itemView.findView(TR.home_friends_message)

  mBase.setTag(this)
  itemView.setOnClickListener(this)

  def onClick(view: View): Unit = {
    itemOnClick.onClick()
  }

}

trait FriendItemExpand {
  def onClick(): Unit
}

trait FriendItemClicks {
  def startOverLayFriend(layoutPosition: Int): Unit
  def startCall(layoutPosition: Int): Unit
  def startMessage(layoutPosition: Int): Unit
}

