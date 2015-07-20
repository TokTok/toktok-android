package im.tox.toktok.app.MessageActivity

import android.content.{Intent}
import android.os.Bundle
import android.support.v4.app.{FragmentTransaction, Fragment}
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.view.View.OnClickListener
import android.view._
import android.widget._
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class MessageActivity extends AppCompatActivity {

  var mMenu: Menu = null
  var mToolbar: Toolbar = null
  var mUserName: TextView = null
  var mStatus: ImageView = null
  var contactColorPrimary: Int = 0
  var contactColorStatus: Int = 0
  var typeOfMessage: Int = 0
  var header: RelativeLayout = null
  var title: String = ""
  var imgSRC: Int = 0


  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_message)

    val bundle: Bundle = getIntent.getExtras

    contactColorPrimary = bundle.getInt("contactColorPrimary")
    contactColorStatus = bundle.getInt("contactColorStatus")
    typeOfMessage = bundle.getInt("messageType")
    title = bundle.getString("messageTitle")
    imgSRC = bundle.getInt("imgResource")


    initToolbar()
    initRecyclerView()
    initInput()


  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {

    item.getItemId() match {

      case R.id.action_recall_message => {

        val recallMessageIntent: Intent = new Intent(this, classOf[MessageRecallActivity])
        val bundle: Bundle = new Bundle()
        bundle.putInt("colorPrimary", contactColorPrimary)
        bundle.putInt("colorPrimaryStatus", contactColorStatus)
        recallMessageIntent.putExtras(bundle)
        startActivity(recallMessageIntent)
        return true

      }
      case R.id.action_group_members => {

        val contactListIntent: Intent = new Intent(this, classOf[MessageGroupContacts])
        val bundle: Bundle = new Bundle()
        bundle.putInt("colorPrimary", contactColorPrimary)
        bundle.putInt("colorPrimaryStatus", contactColorStatus)
        contactListIntent.putExtras(bundle)
        startActivity(contactListIntent)
        return true

      }


      case default => {
        return super.onOptionsItemSelected(item);
      }
    }
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    mMenu = menu

    if (typeOfMessage == 0) {
      getMenuInflater.inflate(R.menu.menu_single_message, menu)
    }
    else {
      getMenuInflater.inflate(R.menu.menu_group_message, menu)
    }

    super.onCreateOptionsMenu(menu)
    return true
  }

  def initToolbar(): Unit = {
    mToolbar = findViewById(R.id.message_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(contactColorPrimary)


    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true);
    getSupportActionBar.setDisplayShowTitleEnabled(false);

    getWindow.setStatusBarColor(contactColorStatus)


    header = findViewById(R.id.message_header).asInstanceOf[RelativeLayout]

    val params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL)

    if (typeOfMessage == 0) {

      header.addView(getLayoutInflater.inflate(R.layout.message_header_user, null, true), params)
      header.findViewById(R.id.message_header_user_img).asInstanceOf[CircularImageView].setImageResource(imgSRC)

    }

    else {

      header.addView(getLayoutInflater.inflate(R.layout.message_header_group, null, true), params)

    }

    mUserName = header.findViewById(R.id.message_header_title).asInstanceOf[TextView]
    mUserName.setText(title)

  }

  def initRecyclerView(): Unit = {

    val mRecycler: RecyclerView = findViewById(R.id.message_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[Message] = new ListBuffer[Message]
    list += new Message(1, "Welcome to TokTok "+title+", I hope you love it, as much as I do \uD83D\uDE00", "14:30 Delivered", R.drawable.user)
    list += new Message(2, "Thanks André Almeida, let's hope soo.", "14:30 Delivered", imgSRC)
    list += new Message(3, "Smiled ", "", imgSRC)


    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new MessageAdapter(list))


    val attachButton: ImageButton = findViewById(R.id.message_attachments_button).asInstanceOf[ImageButton]

    attachButton.setOnClickListener(new OnClickListener {

      override def onClick(v: View): Unit = {
        val attachFragment: Fragment = new MessageAttachments
        val trans: FragmentTransaction = getSupportFragmentManager.beginTransaction()
        trans.add(R.id.message_frame, attachFragment)
        trans.commit()

      }

    })

  }

  def initInput(): Unit = {

    val input: EditText = findViewById(R.id.message_input).asInstanceOf[EditText]

    if (typeOfMessage == 0) {
      input.setHint(getResources.getString(R.string.message_hint_single)+" "+ title)
    }

  }

}