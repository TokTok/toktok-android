package im.tox.toktok.app.MainFriendsFragment

import android.content.{Intent, Context}
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.{LinearLayout, TextView}
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R
import im.tox.toktok.app.ContactsActivity.ContactsActivity


final class FriendsRecyclerViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  var context: Context = itemView.getContext

  itemView.setOnClickListener(this)
  var mUserName: TextView = itemView.findViewById(R.id.home_friends_name).asInstanceOf[TextView]
  var mLayout: LinearLayout = itemView.findViewById(R.id.home_friends_layout).asInstanceOf[LinearLayout]
  //var mUserStatus: TextView = itemView.findViewById(R.id.home_friends_status).asInstanceOf[TextView]
  var mUserImage: CircularImageView = itemView.findViewById(R.id.home_friends_img).asInstanceOf[CircularImageView]


  def onClick(view: View) = {
    val contactIntent: Intent = new Intent(context, classOf[ContactsActivity])
    context.startActivity(contactIntent)

  }

}




