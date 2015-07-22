package im.tox.toktok.app.ContactsActivity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.{CollapsingToolbarLayout, FloatingActionButton}
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.OnClickListener
import android.widget.{ImageView, RelativeLayout, TextView}
import im.tox.toktok.R
import im.tox.toktok.app.CallActivity.CallActivity
import im.tox.toktok.app.SimpleDialogs.{SimpleColorDialogDesign, SimpleDialogDesign, SimpleTextDialogDesign}
import im.tox.toktok.app.VideoCallActivity.VideoCallActivity

class ContactsActivity extends AppCompatActivity {

  final val friendIcons = List(R.id.contacts_icon_call, R.id.contacts_icon_message, R.id.contacts_icon_image, R.id.contacts_icon_download, R.id.contacts_icon_palette, R.id.contacts_icon_edit, R.id.contacts_icon_trash, R.id.contacts_icon_lock)
  var friendTitle: String = ""
  var friendMessage: String = ""
  var friendImgSRC: Int = 0
  var friendColor: Int = 0
  var friendSecondColor: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_contacts)

    val bundle: Bundle = getIntent.getExtras

    friendTitle = bundle.getString("contactName")
    friendMessage = bundle.getString("contactStatusMessage")
    friendColor = bundle.getInt("contactColorPrimary")
    friendSecondColor = bundle.getInt("contactColorSecondary")
    friendImgSRC = bundle.getInt("contactPhotoReference")

    val mFAB: FloatingActionButton = findViewById(R.id.contacts_FAB).asInstanceOf[FloatingActionButton]
    mFAB.setBackgroundTintList(ColorStateList.valueOf(friendColor))

    val mToolbar: Toolbar = findViewById(R.id.contacts_toolbar).asInstanceOf[Toolbar]
    setSupportActionBar(mToolbar)

    val mImage: ImageView = findViewById(R.id.contact_image).asInstanceOf[ImageView]
    mImage.setImageResource(friendImgSRC)

    val mMessage: TextView = findViewById(R.id.contact_message_status).asInstanceOf[TextView]
    mMessage.setText(friendMessage)

    val mCollapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.contacts_collapsing_toolbar).asInstanceOf[CollapsingToolbarLayout]
    mCollapsingToolbar.setTitle(friendTitle)
    mCollapsingToolbar.setBackgroundColor(friendColor)
    mCollapsingToolbar.setContentScrimColor(friendColor)
    mCollapsingToolbar.setStatusBarScrimColor(friendSecondColor)

    getWindow().setStatusBarColor(getResources.getColor(R.color.contactsTransparentBar));

    for (icon <- friendIcons) {

      val obj = findViewById(icon).asInstanceOf[ImageView]
      obj.setImageTintList(ColorStateList.valueOf(friendColor))

    }

    findViewById(R.id.contacts_other_title).asInstanceOf[TextView].setTextColor(friendColor)


    val mDeleteLayout: RelativeLayout = findViewById(R.id.contacts_delete).asInstanceOf[RelativeLayout]
    mDeleteLayout.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleDialogDesign = new SimpleDialogDesign(ContactsActivity.this, getResources.getString(R.string.contact_popup_delete_friend),friendColor, R.drawable.ic_delete_black_48dp, null)
        dial.show()
      }
    })

    val mEditName: RelativeLayout = findViewById(R.id.contacts_edit_alias).asInstanceOf[RelativeLayout]

    mEditName.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleTextDialogDesign = new SimpleTextDialogDesign(ContactsActivity.this, getResources.getString(R.string.contact_popup_edit_alias), friendColor ,R.drawable.ic_person_black_48dp, friendTitle, null)
        dial.show()
      }
    })

    val mColorContact: RelativeLayout = findViewById(R.id.contact_edit_color).asInstanceOf[RelativeLayout]

    mColorContact.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleColorDialogDesign = new SimpleColorDialogDesign(ContactsActivity.this, getResources.getString(R.string.contact_popup_edit_contact_color),friendColor, R.drawable.ic_image_color_lens, 0, null)
        dial.show()
      }
    })

    val mFileSend: RelativeLayout = findViewById(R.id.contacts_file_download).asInstanceOf[RelativeLayout]

    mFileSend.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val newIntent = new Intent(ContactsActivity.this, classOf[FileSendActivity])
        newIntent.putExtras(bundle)
        startActivity(newIntent)
      }
    })

    val mVoiceCall: TextView = findViewById(R.id.contacts_item_voice_call).asInstanceOf[TextView]
    mVoiceCall.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val newIntent = new Intent(ContactsActivity.this, classOf[CallActivity])
        newIntent.putExtras(bundle)
        startActivity(newIntent)
      }
    })


    val mVideoCall: TextView = findViewById(R.id.contacts_item_video_call).asInstanceOf[TextView]
    mVideoCall.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val newIntent = new Intent(ContactsActivity.this, classOf[VideoCallActivity])
        newIntent.putExtras(bundle)
        startActivity(newIntent)
      }
    })


  }

}