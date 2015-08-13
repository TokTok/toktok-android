package im.tox.toktok.app.MessageActivity

import android.content.res.ColorStateList
import android.content.{Context, Intent}
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.design.widget.{FloatingActionButton, Snackbar}
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.{CardView, LinearLayoutManager, RecyclerView, Toolbar}
import android.text.{Editable, TextWatcher}
import android.util.{Log, TypedValue}
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view._
import android.view.animation.Animation.AnimationListener
import android.view.animation.{Animation, AnimationUtils}
import android.view.inputmethod.InputMethodManager
import android.widget._
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.ContactsActivity.FileSendActivity
import im.tox.toktok.app.MainActivity.MainFriendsFragment.SlideInContactsLayout
import im.tox.toktok.app.SimpleDialogs.{SimpleDialogDesign, SimpleTextDialogDesign}
import im.tox.toktok.app.{Friend, Message, SizeAnimation}

import scala.collection.mutable.ListBuffer

class MessageActivity extends AppCompatActivity with MessageClick with MessageActionMode {

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
  var mInput: EditText = null
  var mRecyclerAdapter: MessageAdapter = null
  var mRecycler: RecyclerView = null
  var overlayContactsLayout: SlideInContactsLayout = null
  var overlay_attachments: SlideInAttachmentsLayout = null
  var mActionMode: ActionMode = null
  var actionModeCallback = new MessageActionModeCallback

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

      case android.R.id.home => {
        finish()
        return true
      }

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

      case R.id.action_see_files_list => {

        val bundle = new Bundle

        bundle.putString("contactName", title)
        bundle.putInt("contactColorPrimary", contactColorPrimary)
        bundle.putInt("contactColorStatus", contactColorStatus)

        val newIntent: Intent = new Intent(this, classOf[FileSendActivity])
        newIntent.putExtras(bundle)
        startActivity(newIntent)

        return true

      }

      case R.id.action_leave_conversation => {
        val dial = new SimpleDialogDesign(this, getResources.getString(R.string.dialog_leave_group), contactColorPrimary, R.drawable.ic_delete_black_48dp, null)
        dial.show()
        return true
      }

      case R.id.action_rename_conversation => {
        val dial = new SimpleTextDialogDesign(this, getResources.getString(R.string.dialog_edit_group_name), contactColorPrimary, R.drawable.ic_editor_mode_edit_48, title, null)
        dial.show()
        return true
      }

      case R.id.action_mute_conversation_group => {

        val snack: Snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), getResources.getString(R.string.message_snackbar_group_muted), Snackbar.LENGTH_LONG)
        snack.setAction("Undo", new OnClickListener {
          override def onClick(v: View): Unit = {
            Log.d("asdasd", "asdsad")
          }
        })
        snack.setActionTextColor(contactColorPrimary)
        val snackView: View = snack.getView
        snackView.setBackgroundResource(R.color.snackBarColor)
        snackView.setElevation(10)
        val snackText: TextView = snackView.findViewById(android.support.design.R.id.snackbar_text).asInstanceOf[TextView]
        snackText.setTextColor(getResources.getColor(R.color.textDarkColor))
        snack.show()
        return true

      }

      case R.id.action_delete_conversation => {

        val dial = new SimpleDialogDesign(this, getResources.getString(R.string.dialog_delete_conversion), contactColorPrimary, R.drawable.ic_delete_black_48dp, null)
        dial.show()
        return true
      }

      case R.id.action_mute_conversation_single => {

        val snack: Snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), getResources.getString(R.string.message_snackbar_friend_muted), Snackbar.LENGTH_LONG)
        snack.setAction("Undo", new OnClickListener {
          override def onClick(v: View): Unit = {
            Log.d("asdasd", "asdsad")
          }
        })
        snack.setActionTextColor(contactColorPrimary)
        val snackView: View = snack.getView
        snackView.setBackgroundResource(R.color.snackBarColor)
        snackView.setElevation(10)
        val snackText: TextView = snackView.findViewById(android.support.design.R.id.snackbar_text).asInstanceOf[TextView]
        snackText.setTextColor(getResources.getColor(R.color.textDarkColor))
        snack.show()
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

    overlay_attachments = findViewById(R.id.fragment_attachments_slide).asInstanceOf[SlideInAttachmentsLayout]
    header = findViewById(R.id.message_header).asInstanceOf[RelativeLayout]

    val params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_VERTICAL)

    if (typeOfMessage == 0) {

      header.addView(getLayoutInflater.inflate(R.layout.message_header_user, null, true), params)
      header.findViewById(R.id.message_header_user_img).asInstanceOf[CircleImageView].setImageResource(imgSRC)
      header.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          startOverLayFriend()
        }
      })

    }

    else {

      header.addView(getLayoutInflater.inflate(R.layout.message_header_group, null, true), params)
      header.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {

          val contactListIntent: Intent = new Intent(MessageActivity.this, classOf[MessageGroupContacts])
          val bundle: Bundle = new Bundle()
          bundle.putInt("colorPrimary", contactColorPrimary)
          bundle.putInt("colorPrimaryStatus", contactColorStatus)
          contactListIntent.putExtras(bundle)
          startActivity(contactListIntent)

        }
      })

    }

    mUserName = header.findViewById(R.id.message_header_title).asInstanceOf[TextView]
    mUserName.setText(title)

  }

  def initRecyclerView(): Unit = {

    mRecycler = findViewById(R.id.message_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[Message] = new ListBuffer[Message]



    list += new Message(3, "Smiled ", "", imgSRC)
    list += new Message(2, "Thanks André Almeida, let's hope soo.", "14:30 Delivered", imgSRC)
    list += new Message(1, "Welcome to TokTok " + title + ", I hope you love it, as much as I do \uD83D\uDE00", "14:30 Delivered", R.drawable.user)

    if (imgSRC == 0) {
      imgSRC = R.drawable.lorem
      list += new Message(3, "The Amazing Group was created", "", R.drawable.user)
    }


    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mLayoutManager.setReverseLayout(true)
    mRecycler.setHasFixedSize(true)
    mRecyclerAdapter = new MessageAdapter(list, this, this)

    mRecycler.setAdapter(mRecyclerAdapter)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setItemAnimator(new MessageItemAnimator)


    val attachButton: ImageButton = findViewById(R.id.message_attachments_button).asInstanceOf[ImageButton]

    attachButton.setOnClickListener(new OnClickListener {

      override def onClick(v: View): Unit = {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
        imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken, 0)

        overlay_attachments.start()

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

  def onImgClick(): Unit = {
    startOverLayFriend()
  }


  def startOverLayFriend(): Unit = {


    overlayContactsLayout = getLayoutInflater.inflate(R.layout.overlay_contacts, null).asInstanceOf[SlideInContactsLayout]
    val params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, PixelFormat.TRANSLUCENT)
    val window = getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]

    window.addView(overlayContactsLayout, params)

    var actionBarHeight = 0;

    val tv = new TypedValue();
    if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
    }

    overlayContactsLayout.start(this, Friend.lorem, actionBarHeight)

  }

  override def onBackPressed(): Unit = {
    if (overlay_attachments.getVisibility == View.INVISIBLE) {
      finish()
    }
    else {
      overlay_attachments.finish()
    }
  }

  def onClick(i: Int) = {
    if (mActionMode != null) {

      mRecyclerAdapter.toggleSelection(i)

      val count = mRecyclerAdapter.getSelectedItemCount()

      if (count == 0) {
        mActionMode.finish()
      }
      else if (count == 1) {
        mActionMode.setTitle(count + " " + getResources.getString(R.string.action_mode_selected_single))
      }
      else {
        mActionMode.setTitle(count + " " + getResources.getString(R.string.action_mode_selected_multi))
      }

    }

  }

  def onLongClick(i: Int): Boolean = {
    if (mActionMode == null) {
      mActionMode = startSupportActionMode(actionModeCallback)
    }

    mRecyclerAdapter.toggleSelection(i)

    val count = mRecyclerAdapter.getSelectedItemCount()

    if (count == 0) {
      mActionMode.finish()
    }
    else if (count == 1) {
      mActionMode.setTitle(count + " " + getResources.getString(R.string.action_mode_selected_single))
    }
    else {
      mActionMode.setTitle(count + " " + getResources.getString(R.string.action_mode_selected_multi))
    }

    return true
  }

  class MessageActionModeCallback() extends ActionMode.Callback {

    override def onDestroyActionMode(mode: ActionMode): Unit = {
      mActionMode = null
      mRecyclerAdapter.setActionModeActive(false)
      mRecyclerAdapter.clearSelections()
      val animationIn = AnimationUtils.loadAnimation(mInputLayout.getContext, R.anim.abc_fade_in)
      mInputLayout.setVisibility(View.VISIBLE)
      mInputLayout.startAnimation(animationIn)
    }

    override def onCreateActionMode(mode: ActionMode, menu: Menu): Boolean = {
      val menuInflater = mode.getMenuInflater
      menuInflater.inflate(R.menu.message_action_mode, menu)

      mRecyclerAdapter.setActionModeActive(true)
      mRecyclerAdapter.notifyDataSetChanged()

      val animationOut = AnimationUtils.loadAnimation(mInputLayout.getContext, R.anim.abc_slide_out_bottom)
      mInputLayout.startAnimation(animationOut)
      mInputLayout.setVisibility(View.GONE)

      return true
    }

    override def onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean = {
      item.getItemId match {

        case R.id.action_message_delete => {
          mRecyclerAdapter.deleteSelected()
          mode.finish()
        }

      }

      return true
    }

    override def onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = {
      return false
    }

  }

}