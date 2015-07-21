package im.tox.toktok.app.NewMessageActivity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.view.{MenuItem, Window, WindowManager}
import android.widget.LinearLayout
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer

class NewMessageActivity extends AppCompatActivity {

  var mToolbar: Toolbar = null
  var mRecycler: RecyclerView = null
  var colorPrimary: Int = 0
  var colorStatus: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_message)

    val bundle: Bundle = getIntent.getExtras

    if(bundle != null) {

      colorPrimary = bundle.getInt("colorPrimary")
      colorStatus = bundle.getInt("colorPrimaryStatus")
    }
    else{

      colorPrimary = Color.parseColor("#F5A623")
      colorStatus = Color.parseColor("#8C5F14")

    }

    initToolbar(colorPrimary,colorStatus)
    initRecyclerView()


  }

  def initRecyclerView(): Unit = {

    mRecycler = findViewById(R.id.new_message_recycler).asInstanceOf[RecyclerView];

    val mLayoutManager: LayoutManager = new LinearLayoutManager(this)

    val a = ListBuffer(Friend.bart, Friend.jane, Friend.john, Friend.lorem)

    val mFriends_Recycler_Adapter: NewMessageRecyclerHeaderAdapter = new NewMessageRecyclerHeaderAdapter(a)

    mRecycler.setAdapter(mFriends_Recycler_Adapter)
    mRecycler.setLayoutManager(mLayoutManager)
    mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))
  }

  def initToolbar(colour: Int, secondColour: Int): Unit = {
    mToolbar = findViewById(R.id.newMessage_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(colour)

    findViewById(R.id.new_message_app_bar_layout).asInstanceOf[AppBarLayout].setBackgroundColor(colour)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setTitle(getResources.getString(R.string.new_message_title))
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    val window: Window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.setStatusBarColor(secondColour);


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

}
