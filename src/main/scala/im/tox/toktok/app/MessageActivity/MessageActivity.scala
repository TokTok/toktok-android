package im.tox.toktok.app.MessageActivity

import android.content.{Context, Intent}
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{CardView, LinearLayoutManager, RecyclerView, Toolbar}
import android.text.{Editable, TextWatcher}
import android.util.Log
import android.view.View.OnClickListener
import android.view._
import android.view.animation.Animation.AnimationListener
import android.view.animation.{Animation, AnimationUtils}
import android.view.inputmethod.InputMethodManager
import android.widget._
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.SimpleDialogs.SimpleDialogDesign
import im.tox.toktok.app.{Message, SizeAnimation}

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
  var mFab: FloatingActionButton = null
  var mSendButtonActive: Boolean = false
  var title: String = ""
  var imgSRC: Int = 0
  var mInputLayout: CardView = null
  var mInput : EditText = null
  var mRecyclerAdapter: MessageAdapter = null
  var mRecycler : RecyclerView = null

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

      case R.id.action_delete_conversation => {

        val dial = new SimpleDialogDesign(this, getResources.getString(R.string.dialog_delete_conversion), contactColorPrimary, R.drawable.ic_delete_black_48dp, null)
        dial.show()
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
      header.findViewById(R.id.message_header_user_img).asInstanceOf[CircleImageView].setImageResource(imgSRC)

    }

    else {

      header.addView(getLayoutInflater.inflate(R.layout.message_header_group, null, true), params)

    }

    mUserName = header.findViewById(R.id.message_header_title).asInstanceOf[TextView]
    mUserName.setText(title)

  }

  def initRecyclerView(): Unit = {

    mRecycler = findViewById(R.id.message_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[Message] = new ListBuffer[Message]

    if (imgSRC == 0) {
      imgSRC = R.drawable.lorem
      list += new Message(3, "The Amazing Group was created", "", R.drawable.user)
    }

    list += new Message(3, "Smiled ", "", imgSRC)
    list += new Message(2, "Thanks André Almeida, let's hope soo.", "14:30 Delivered", imgSRC)
    list += new Message(1, "Welcome to TokTok " + title + ", I hope you love it, as much as I do \uD83D\uDE00", "14:30 Delivered", R.drawable.user)


    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mLayoutManager.setReverseLayout(true)
    mRecycler.setHasFixedSize(true)
    mRecyclerAdapter = new MessageAdapter(list)

    mRecycler.setAdapter(mRecyclerAdapter)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setItemAnimator(new MessageItemAnimator)


    val attachButton: ImageButton = findViewById(R.id.message_attachments_button).asInstanceOf[ImageButton]

    attachButton.setOnClickListener(new OnClickListener {

      override def onClick(v: View): Unit = {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
        imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken,0)

        val overlay_attachments = findViewById(R.id.fragment_attachments_slide).asInstanceOf[SlideInAttachmentsLayout]
        overlay_attachments.setVisibility(View.VISIBLE)
        overlay_attachments.smoothSlideTo(0)

      }

    })

  }

  def initInput(): Unit = {

    mInput = findViewById(R.id.message_input).asInstanceOf[EditText]
    mInputLayout = findViewById(R.id.message_input_cardview).asInstanceOf[CardView]
    mFab = findViewById(R.id.message_fab).asInstanceOf[FloatingActionButton]
    mFab.setBackgroundTintList(ColorStateList.valueOf(contactColorPrimary))

    mFab.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        mRecyclerAdapter.addItem(new Message(1, mInput.getText.toString, "14:30 Delivered", R.drawable.user))
        mRecycler.smoothScrollToPosition(0)
        mInput.setText("")

      }
    })


    if (typeOfMessage == 0) {
      mInput.setHint(getResources.getString(R.string.message_hint_single) + " " + title)
    }

    mInput.addTextChangedListener(new TextWatcher {

      override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int): Unit = {
      }

      override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int): Unit = {

        val countVal = mInput.getText.length()

        if (countVal > 0 && !mSendButtonActive) {

          shrinkInputBar()

        }
        else if (countVal == 0 && mSendButtonActive) {

          expandInputBar()

        }

      }

      override def afterTextChanged(s: Editable): Unit = {
      }

    })

  }

  def shrinkInputBar(): Unit = {
    val animationInput = new SizeAnimation(mInputLayout, mInputLayout.getWidth - mFab.getWidth - mFab.getPaddingRight, 0)
    animationInput.setDuration(250)
    mInputLayout.startAnimation(animationInput)

    val animationButton = AnimationUtils.loadAnimation(this, R.anim.fab_from_right)
    animationButton.setDuration(250)

    animationButton.setAnimationListener(new AnimationListener {
      override def onAnimationEnd(animation: Animation): Unit = {}

      override def onAnimationStart(animation: Animation): Unit = {
        mFab.setVisibility(View.VISIBLE)
      }

      override def onAnimationRepeat(animation: Animation): Unit = {}
    })

    mFab.startAnimation(animationButton)

    mSendButtonActive = true


  }

  def expandInputBar(): Unit = {
    val animationInput = new SizeAnimation(mInputLayout, mInputLayout.getWidth + mFab.getWidth + mFab.getPaddingRight, 0)
    animationInput.setDuration(250)
    mInputLayout.startAnimation(animationInput)

    val animationButton = AnimationUtils.loadAnimation(this, R.anim.fab_to_right)
    animationButton.setDuration(250)

    animationButton.setAnimationListener(new AnimationListener {
      override def onAnimationEnd(animation: Animation): Unit = {
        mFab.setVisibility(View.INVISIBLE)
      }

      override def onAnimationStart(animation: Animation): Unit = {}

      override def onAnimationRepeat(animation: Animation): Unit = {}
    })

    mFab.startAnimation(animationButton)

    mSendButtonActive = false

  }

}