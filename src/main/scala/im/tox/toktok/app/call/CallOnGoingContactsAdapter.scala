package im.tox.toktok.app.call

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, ViewGroup}
import android.widget.{RelativeLayout, TextView}
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.TR
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.domain.Friend

final class CallOnGoingContactsAdapter(friends: java.util.List[Friend]) extends RecyclerView.Adapter[CallOnGoingContactsViewHolder] {

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CallOnGoingContactsViewHolder = {
    val itemView: RelativeLayout = LayoutInflater.from(viewGroup.getContext).inflate(TR.layout.call_ongoing_contact, viewGroup, false)
    new CallOnGoingContactsViewHolder(itemView)
  }

  def onBindViewHolder(viewHolder: CallOnGoingContactsViewHolder, position: Int): Unit = {
    val item: Friend = friends.get(position)
    viewHolder.mFriendName.setText(item.userName)
    viewHolder.mFriendImage.setImageResource(item.photoReference)
  }

  def getItemCount: Int = {
    friends.size()
  }

}

final class CallOnGoingContactsViewHolder(itemView: RelativeLayout) extends RecyclerView.ViewHolder(itemView) {
  val mFriendImage: CircleImageView = itemView.findView(TR.call_ongoing_contact_img)
  val mFriendName: TextView = itemView.findView(TR.call_ongoing_contact_name)
  val mFriendCallTime: TextView = itemView.findView(TR.call_ongoing_contact_calltime)
}
