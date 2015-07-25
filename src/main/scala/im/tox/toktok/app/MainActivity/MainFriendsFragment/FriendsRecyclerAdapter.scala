package im.tox.toktok.app.MainActivity.MainFriendsFragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.Log
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget._
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R
import im.tox.toktok.app.ContactsActivity.ContactsActivity
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer

class FriendsRecyclerAdapter(list: ListBuffer[Friend]) extends RecyclerView.Adapter[FriendsRecyclerViewHolder] {

  private val items: ListBuffer[Friend] = list
  private var expandedItem: Int = -1;

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FriendsRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.fragment_home_friends_item, viewGroup, false)
    return new FriendsRecyclerViewHolder(itemView, new FriendItemOnClick {
      override def onClick(): Unit = {

        val holder = itemView.getTag.asInstanceOf[ViewHolder]

        if (expandedItem == holder.getLayoutPosition) {

          notifyItemChanged(expandedItem)
          expandedItem = -1

        }
        else {

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
    val item: Friend = items(position)
    viewHolder.mUserName.setText(item.getUserName())
    viewHolder.mUserImage.setImageResource(item.getPhotoReference())

    if (position == expandedItem) {

      viewHolder.mExpandedArea.setVisibility(View.VISIBLE)
      viewHolder.mBase.setElevation(10)
      viewHolder.mBase.setBackgroundResource(R.drawable.cardboard_ripple)
      viewHolder.mUserImage.setClickable(true)


      viewHolder.mCallButton.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          Log.d("TokTok", "Call")
        }
      })

      viewHolder.mMessageButton.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          Log.d("TokTok", "Message")
        }
      })

      viewHolder.mUserImage.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          val friendInfo = items(position)

          val bundle: Bundle = new Bundle()

          bundle.putInt("contactColorPrimary", friendInfo.getColor())
          bundle.putInt("contactColorSecondary", friendInfo.getSecondColor())
          bundle.putInt("contactPhotoReference", friendInfo.getPhotoReference())
          bundle.putString("contactName", friendInfo.getUserName())
          bundle.putString("contactStatusMessage", friendInfo.getUserMessage())

          val context = v.getContext

          val contactIntent: Intent = new Intent(context, classOf[ContactsActivity])
          contactIntent.putExtras(bundle)

          context.startActivity(contactIntent)

        }
      })

    }
    else {

      viewHolder.mExpandedArea.setVisibility(View.GONE)
      viewHolder.mBase.setElevation(0)
      viewHolder.mBase.setBackgroundResource(R.drawable.background_ripple)

      viewHolder.mCallButton.setOnClickListener(null)
      viewHolder.mMessageButton.setOnClickListener(null)

      viewHolder.mCallButton.setOnClickListener(null)
      viewHolder.mMessageButton.setOnClickListener(null)
      viewHolder.mUserImage.setOnClickListener(null)
      viewHolder.mUserImage.setClickable(false)

    }

  }

  def getItemCount(): Int = {
    return items.length
  }

  def getItemPosition(i: Int): String = {
    return items(i).getUserName()
  }


}

class FriendsRecyclerViewHolder(itemView: View, itemOnClick: FriendItemOnClick) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  var mBase: RelativeLayout = itemView.findViewById(R.id.home_friends_base).asInstanceOf[RelativeLayout]
  var mUserInfo: RelativeLayout = itemView.findViewById(R.id.home_item_info).asInstanceOf[RelativeLayout]
  var mUserName: TextView = itemView.findViewById(R.id.home_friends_name).asInstanceOf[TextView]
  var mUserImage: CircularImageView = itemView.findViewById(R.id.home_friends_img).asInstanceOf[CircularImageView]
  var mExpandedArea: LinearLayout = itemView.findViewById(R.id.home_friends_expanded_area).asInstanceOf[LinearLayout]
  var mCallButton = itemView.findViewById(R.id.home_friends_call)
  var mMessageButton = itemView.findViewById(R.id.home_friends_message)
  var onClickListener: FriendItemOnClick = itemOnClick

  mBase.setTag(this)
  itemView.setOnClickListener(this)

  def onClick(view: View): Unit = {

    onClickListener.onClick()

  }

}

trait FriendItemOnClick {
  def onClick()
}


