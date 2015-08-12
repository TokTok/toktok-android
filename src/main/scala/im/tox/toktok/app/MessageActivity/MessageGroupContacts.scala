package im.tox.toktok.app.MessageActivity

import android.content.{Context, Intent}
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.util.TypedValue
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.{WindowManager, MenuItem, View}
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.CallActivity.CallActivity
import im.tox.toktok.app.{MainActivityHolder, Friend}
import im.tox.toktok.app.MainActivity.MainFriendsFragment.{SlideInContactsLayout, FriendItemClicks, FriendsRecyclerHeaderAdapter}
import im.tox.toktok.app.NewMessageActivity.NewMessageActivity

import scala.collection.mutable.ListBuffer


class MessageGroupContacts extends AppCompatActivity with FriendItemClicks {

  var adapter : FriendsRecyclerHeaderAdapter = null
  var colorPrimary: Int = 0
  var colorStatus: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_message_group_members)

    val bundle: Bundle = getIntent.getExtras

    colorPrimary = bundle.getInt("colorPrimary")
    colorStatus = bundle.getInt("colorPrimaryStatus")

    val mToolbar: Toolbar = findViewById(R.id.message_group_members_toolbar).asInstanceOf[Toolbar]
    mToolbar.setTitle(getResources.getString(R.string.message_group_contacts))
    mToolbar.setBackgroundColor(colorPrimary)
    getWindow.setStatusBarColor(colorStatus)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)


    val mFAB: FloatingActionButton = findViewById(R.id.message_group_members_fab).asInstanceOf[FloatingActionButton]

    mFAB.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val addContactView: Intent = new Intent(MessageGroupContacts.this, classOf[NewMessageActivity])
        addContactView.putExtras(bundle)
        startActivity(addContactView)

      }
    })

    val mRecycler: RecyclerView = findViewById(R.id.message_group_members_recycler).asInstanceOf[RecyclerView]

    val list: ListBuffer[Friend] = ListBuffer(Friend.lorem, Friend.john)

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    adapter = new FriendsRecyclerHeaderAdapter(list,this)
    mRecycler.setAdapter(adapter)
    mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter))

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

  def startOverLayFriend(friendPosition: Int): Unit = {


    val layout = getLayoutInflater.inflate(R.layout.overlay_contacts, null).asInstanceOf[SlideInContactsLayout]
    val params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS , PixelFormat.TRANSLUCENT)
    val window = getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]


    window.addView(layout, params)

    var actionBarHeight = 0;

    val tv = new TypedValue();
    if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
    }

    layout.start(this,adapter.getItem(friendPosition), actionBarHeight)

  }

  def startCall(friendPosition: Int): Unit = {

    val friend  = adapter.getItem(friendPosition)

    val bundle = new Bundle
    bundle.putString("contactName", friend.getUserName)
    bundle.putInt("contactColorPrimary", friend.getColor)
    bundle.putInt("contactPhotoReference", friend.getPhotoReference)


    val newIntent: Intent = new Intent(this, classOf[CallActivity])
    newIntent.putExtras(bundle)
    startActivity(newIntent)

  }

}