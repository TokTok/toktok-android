package im.tox.toktok.app.MainActivity.MainFriendsFragment

import android.content.{Intent, Context}
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.{Log}
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{ImageButton, LinearLayout, TextView}
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R
import im.tox.toktok.app.ContactsActivity.ContactsActivity
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer

class FriendsRecyclerAdapter(list: ListBuffer[Friend]) extends RecyclerView.Adapter[FriendsRecyclerViewHolder] {

  private val items: ListBuffer[Friend] = list
  private var active : View = null;

  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FriendsRecyclerViewHolder = {
    val itemView: View = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.fragment_home_friends_item, viewGroup, false)
    return new FriendsRecyclerViewHolder(itemView,list,active)
  }

  def onBindViewHolder(viewHolder: FriendsRecyclerViewHolder, position: Int) = {
    val item: Friend = items(position)
    viewHolder.mUserName.setText(item.getUserName())
    viewHolder.mUserImage.setImageResource(item.getPhotoReference())

  }

  def getItemCount(): Int = {
    return items.length
  }

  def getItemPosition(i: Int): String = {
    return items(i).getUserName()
  }

}

class FriendsRecyclerViewHolder(itemView: View, list: ListBuffer[Friend],active : View) extends RecyclerView.ViewHolder(itemView) with View.OnClickListener {

  var context: Context = itemView.getContext

  itemView.setOnClickListener(this)
  var mUserName: TextView = itemView.findViewById(R.id.home_friends_name).asInstanceOf[TextView]
  var mLayout: LinearLayout = itemView.findViewById(R.id.home_friends_layout).asInstanceOf[LinearLayout]
  //var mUserStatus: TextView = itemView.findViewById(R.id.home_friends_status).asInstanceOf[TextView]
  var mUserImage: CircularImageView = itemView.findViewById(R.id.home_friends_img).asInstanceOf[CircularImageView]
  var mExpandButton : ImageButton = itemView.findViewById(R.id.home_friends_expend).asInstanceOf[ImageButton]
  var mCallButton = itemView.findViewById(R.id.home_friends_call)
  var mMessageButton = itemView.findViewById(R.id.home_friends_message)


  mCallButton.setOnClickListener(new OnClickListener {
    override def onClick(v: View): Unit = {
      Log.d("Tox","Call")
    }
  })


  mMessageButton.setOnClickListener(new OnClickListener {
    override def onClick(v: View): Unit = {
      Log.d("Tox","Message")
    }
  })


  mExpandButton.setOnClickListener(new OnClickListener {
    override def onClick(v: View): Unit ={

      v.setVisibility(View.INVISIBLE)
      itemView.setElevation(5)
      itemView.setBackground(itemView.getResources.getDrawable(R.drawable.cardboard_ripple))
      itemView.findViewById(R.id.home_friends_expended_area).setVisibility(View.VISIBLE)

    }


  })


  def onClick(view: View) = {

    val friend = list(getAdapterPosition)

    val bundle: Bundle = new Bundle()

    bundle.putInt("contactColorPrimary",friend.getColor())
    bundle.putInt("contactColorSecondary",friend.getSecondColor())
    bundle.putInt("contactPhotoReference",friend.getPhotoReference())
    bundle.putString("contactName",friend.getUserName())
    bundle.putString("contactStatusMessage",friend.getUserMessage())

    val contactIntent: Intent = new Intent(context, classOf[ContactsActivity])
    contactIntent.putExtras(bundle)

    context.startActivity(contactIntent)

  }

}
