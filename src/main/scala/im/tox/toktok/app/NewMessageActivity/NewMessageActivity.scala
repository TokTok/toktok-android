package im.tox.toktok.app.NewMessageActivity


import android.graphics.drawable.BitmapDrawable
import android.graphics.{Bitmap, Canvas, Color}
import android.os.Bundle
import android.support.design.widget.{AppBarLayout, FloatingActionButton}
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.text._
import android.text.method.LinkMovementMethod
import android.text.style.{ClickableSpan, ImageSpan}
import android.util.Log
import android.view.View.{MeasureSpec, OnClickListener}
import android.view.ViewGroup.LayoutParams
import android.view._
import android.view.animation.{AccelerateInterpolator, Animation, DecelerateInterpolator, Transformation}
import android.widget.{EditText, ImageButton, LinearLayout, TextView}
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.{Friend, MyRecyclerScroll}

import scala.collection.mutable.ListBuffer

class NewMessageActivity extends AppCompatActivity {

  private var mToolbar: Toolbar = null
  private var mAppBarLayout: AppBarLayout = null;
  private var mRecycler: RecyclerView = null
  private var mSelectedFriends: LinearLayout = null
  private var mFriends_Recycler_Adapter: NewMessageRecyclerHeaderAdapter = null
  private var mFab: FloatingActionButton = null
  private var colorPrimary: Int = 0
  private var colorStatus: Int = 0
  private var mSearchField: EditText = null
  private var mSelectedFriendsText: TextView = null
  private var mSelectedFriendsImg: CircleImageView = null
  private var mSelectedFriendsCounter: TextView = null
  private var mSelectedFriendsButton: ImageButton = null
  private var mSelectedMini: TextView = null
  private var mSelectedMiniExtended: Boolean = false

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_message)

    val bundle: Bundle = getIntent.getExtras

    if (bundle != null) {

      colorPrimary = bundle.getInt("colorPrimary")
      colorStatus = bundle.getInt("colorPrimaryStatus")

    }
    else {

      colorPrimary = Color.parseColor("#F5A623")
      colorStatus = Color.parseColor("#8C5F14")

    }

    mFab = findViewById(R.id.new_message_fab).asInstanceOf[FloatingActionButton]

    initToolbar(colorPrimary, colorStatus)
    initRecyclerView()


  }

  def initRecyclerView(): Unit = {

    mRecycler = findViewById(R.id.new_message_recycler).asInstanceOf[RecyclerView];

    val mLayoutManager: LayoutManager = new LinearLayoutManager(this)

    val a = ListBuffer(Friend.bart, Friend.jane, Friend.john, Friend.lorem)

    mFriends_Recycler_Adapter = new NewMessageRecyclerHeaderAdapter(a, null)

    mFriends_Recycler_Adapter.setlistener(new FriendAddOnClick {
      override def onClickListener(position: Int): Unit = {

        mFriends_Recycler_Adapter.selectItem(position)
        selectItem(position)

      }
    })

    mRecycler.setAdapter(mFriends_Recycler_Adapter)
    mRecycler.setLayoutManager(mLayoutManager)
    mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))

    mRecycler.addOnScrollListener(new MyRecyclerScroll {

      override def hide(): Unit = {
        mFab.animate().translationY(mFab.getHeight() + mFab.getBottom).setInterpolator(new AccelerateInterpolator(2)).start();

      }

      override def show(): Unit = {
        mFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
      }
    })


  }

  def initToolbar(colour: Int, secondColour: Int): Unit = {
    mSelectedFriends = findViewById(R.id.new_message_selected_base).asInstanceOf[LinearLayout]
    mSelectedFriendsImg = findViewById(R.id.new_message_selected_img).asInstanceOf[CircleImageView]
    mSelectedFriendsText = findViewById(R.id.new_message_toolbar_selected_text).asInstanceOf[TextView]
    mSelectedFriendsButton = findViewById(R.id.new_message_toolbar_selected_button).asInstanceOf[ImageButton]


    mToolbar = findViewById(R.id.newMessage_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(colour)

    findViewById(R.id.new_message_app_bar_layout).asInstanceOf[AppBarLayout].setBackgroundColor(colour)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setTitle(getResources.getString(R.string.new_message_title))
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    mAppBarLayout = findViewById(R.id.new_message_app_bar_layout).asInstanceOf[AppBarLayout]

    val window: Window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.setStatusBarColor(secondColour);

    mSearchField = findViewById(R.id.new_message_search_field).asInstanceOf[EditText]

    mSearchField.addTextChangedListener(new TextWatcher {

      override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int): Unit = {}

      override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int): Unit = {

        mFriends_Recycler_Adapter.getFilter.filter(s)

      }

      override def afterTextChanged(s: Editable): Unit = {}
    })


  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {

    item.getItemId match {

      case android.R.id.home => {
        finish()
        return true
      }
    }

    return super.onOptionsItemSelected(item)

  }

  def initFirstSelectedContacts(adapter: NewMessageRecyclerAdapter): Unit = {

    mSelectedFriends.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    val height = mSelectedFriends.getMeasuredHeight

    mSelectedFriends.setVisibility(View.VISIBLE)
    mSelectedFriends.getLayoutParams.height = 1

    val inAnimation = new Animation() {
      override def applyTransformation(time: Float, transf: Transformation) {
        if (time == 1) {
          mSelectedFriends.getLayoutParams.height = LayoutParams.MATCH_PARENT
        }
        else {
          mSelectedFriends.getLayoutParams.height = (time * height).toInt
        }
        mSelectedFriends.requestLayout()
      }

      override def willChangeBounds(): Boolean = {
        return true
      }

    }



    setOneSelectedContact(adapter)

    mFab.show()

    inAnimation.setDuration(4 * (height / mSelectedFriends.getContext.getResources.getDisplayMetrics.density).toInt)
    mSelectedFriends.startAnimation(inAnimation)

  }

  def destroySelectedContacts(): Unit = {

    val height = mSelectedFriends.getMeasuredHeight

    val outAnimation = new Animation() {
      override def applyTransformation(time: Float, transf: Transformation) {
        if (time == 1) {
          mSelectedFriends.setVisibility(View.GONE)
        }
        else {
          mSelectedFriends.getLayoutParams.height = height - (time * height).toInt
          mSelectedFriends.requestLayout()
        }
      }

      override def willChangeBounds(): Boolean = {
        return true
      }

    }

    outAnimation.setDuration(4 * (height / mSelectedFriends.getContext.getResources.getDisplayMetrics.density).toInt)
    mSelectedFriends.startAnimation(outAnimation)

    mFab.hide()


  }

  def destroySelectedContactsMini(): Unit = {
    mSelectedMini.setVisibility(View.GONE)
    mSelectedMini.setText("")
    mSelectedMiniExtended = false
  }

  def setOneSelectedContact(adapter: NewMessageRecyclerAdapter): Unit = {

    val first = adapter.getFirstSelected()

    if (mSelectedMiniExtended) {
      destroySelectedContactsMini()
    }

    mSelectedFriendsImg.setImageResource(first.getPhotoReference())
    mSelectedFriendsText.setText(first.getUserName())
    mSelectedFriendsButton.setImageResource(R.drawable.ic_content_clear)
    mSelectedFriendsButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        adapter.clearSelectedList()
        destroySelectedContacts()
      }
    })

  }

  def setMultiSelectedContact(adapter: NewMessageRecyclerAdapter, selectedFriends: Int): Unit = {

    mSelectedFriendsCounter = findViewById(R.id.new_message_selected_counter).asInstanceOf[TextView]
    mSelectedFriendsCounter.setText(selectedFriends + "")

    mSelectedFriendsText.setText(getResources.getString(R.string.new_message_selected_friends))
    mSelectedFriendsImg.setImageResource(R.color.backgroundColor)

    mSelectedFriendsButton.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down)
    mSelectedFriendsButton.setOnClickListener(new OnClickListener {


      override def onClick(v: View): Unit = {

        if (mSelectedMiniExtended) {
          destroySelectedContactsMini()
          mSelectedFriendsButton.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down)

        }
        else {
          mSelectedMini.setVisibility(View.VISIBLE)
          mSelectedMiniExtended = true
          mSelectedFriendsButton.setImageResource(R.drawable.ic_hardware_keyboard_arrow_up)
          createMiniContact(adapter)
        }
      }
    })
  }

  def createMiniContact(adapter: NewMessageRecyclerAdapter): Unit = {

    var friendsList: CharSequence = ""

    for (friend <- adapter.getSelectedFriends()) {

      val sb = new SpannableStringBuilder();
      val miniContact = createContactTextView(friend.getUserName());
      val bd = convertViewToDrawable(miniContact).asInstanceOf[BitmapDrawable]
      bd.setBounds(0, 0, bd.getIntrinsicWidth() * 3, bd.getIntrinsicHeight() * 3);

      sb.append(friend.getUserName() + " ");
      sb.setSpan(new ImageSpan(bd), sb.length() - (friend.getUserName().length() + 1), sb.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      mSelectedMini.setMovementMethod(LinkMovementMethod.getInstance())
      sb.setSpan(new ClickableSpan {
        override def onClick(widget: View): Unit = {

          val items = adapter.getItems()

          var item : Friend = null;
          var i : Int = 0

          for(item <- items){
            if(item.getID() == friend.getID()){
              adapter.selectItem(i)
              selectItem(i)
            }
            i += 1
          }

        }
      }, sb.length() - (friend.getUserName().length() + 1), sb.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

      friendsList = TextUtils.concat(friendsList, sb)

    }

    mSelectedMini.setText(friendsList);

  }

  def convertViewToDrawable(view: View): Object = {
    val spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

    view.measure(spec, spec);
    view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

    val bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

    val canvas = new Canvas(bitmap);
    canvas.translate(-view.getScrollX(), -view.getScrollY());
    view.draw(canvas);

    view.setDrawingCacheEnabled(true);

    val cacheBmp = view.getDrawingCache();
    val viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);

    view.destroyDrawingCache();
    return new BitmapDrawable(viewBmp);

  }

  def createContactTextView(text: String): LinearLayout = {
    val tv = getLayoutInflater.inflate(R.layout.new_message_toolbar_friend_mini, null).asInstanceOf[LinearLayout]
    tv.getChildAt(0).asInstanceOf[TextView].setText(text);

    return tv;
  }

  def selectItem(position : Int): Unit ={
    if (mSelectedFriends.getVisibility == View.GONE) {
      initFirstSelectedContacts(mFriends_Recycler_Adapter)
    }
    else {

      val selectedFriends = mFriends_Recycler_Adapter.countSelected()

      if (selectedFriends == 0) {

        destroySelectedContacts()

      }
      else if (selectedFriends == 1) {


        mSelectedFriendsCounter.setText("")
        mSelectedFriendsCounter = null

        setOneSelectedContact(mFriends_Recycler_Adapter)

      }
      else {

        setMultiSelectedContact(mFriends_Recycler_Adapter, selectedFriends)
        mSelectedMini = findViewById(R.id.new_message_toolbar_mini).asInstanceOf[TextView]

        val friend = mFriends_Recycler_Adapter.getItem(position)

        if (mSelectedMiniExtended) {

          createMiniContact(mFriends_Recycler_Adapter)

        }
      }
    }
  }

}
