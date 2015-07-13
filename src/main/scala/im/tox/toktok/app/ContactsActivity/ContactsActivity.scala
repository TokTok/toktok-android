package im.tox.toktok.app.ContactsActivity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.{CollapsingToolbarLayout, FloatingActionButton}
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import im.tox.toktok.R
import im.tox.toktok.app.SimpleDialogs.{SimpleColorDialogDesign, SimpleDialogDesign, SimpleTextDialogDesign}

class ContactsActivity extends AppCompatActivity {

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_contacts)

    getWindow.getDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    getWindow.setStatusBarColor(Color.parseColor("#2b000000"))

    val mFAB: FloatingActionButton = findViewById(R.id.contacts_FAB).asInstanceOf[FloatingActionButton]
    mFAB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DC4254")))

    val mToolbar: Toolbar = findViewById(R.id.contacts_toolbar).asInstanceOf[Toolbar]
    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true);


    val mCollapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.contacts_collapsing_toolbar).asInstanceOf[CollapsingToolbarLayout]
    mCollapsingToolbar.setTitle("Lorem Ipsum")
    mCollapsingToolbar.setBackgroundColor(Color.parseColor("#DC4254"))
    mCollapsingToolbar.setContentScrimColor(Color.parseColor("#DC4254"))
    getWindow().setStatusBarColor(Color.parseColor("#2b000000"));


    val mDeleteLayout: RelativeLayout = findViewById(R.id.contacts_delete).asInstanceOf[RelativeLayout]
    mDeleteLayout.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleDialogDesign = new SimpleDialogDesign(ContactsActivity.this, "Delete friend ?", R.drawable.ic_delete_black_48dp, null)
        dial.show()
      }
    })

    val mEditName: RelativeLayout = findViewById(R.id.contacts_edit_alias).asInstanceOf[RelativeLayout]

    mEditName.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleTextDialogDesign = new SimpleTextDialogDesign(ContactsActivity.this, "Edit alias", R.drawable.ic_person_black_48dp, "Lorem Ipsum", null)
        dial.show()
      }
    })

    val mColorContact: RelativeLayout = findViewById(R.id.contact_edit_color).asInstanceOf[RelativeLayout]

    mColorContact.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleColorDialogDesign = new SimpleColorDialogDesign(ContactsActivity.this, "Edit contact color", R.drawable.ic_image_color_lens, 0, null)
        dial.show()
      }
    })

    val mFileSend: RelativeLayout = findViewById(R.id.contacts_file_download).asInstanceOf[RelativeLayout]

    mFileSend.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        startActivity(new Intent(ContactsActivity.this, classOf[FileSendActivity]))
      }
    })

  }

}