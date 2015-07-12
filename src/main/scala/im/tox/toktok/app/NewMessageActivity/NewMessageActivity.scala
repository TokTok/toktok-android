package im.tox.toktok.app.NewMessageActivity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.view.{Window, WindowManager}
import android.widget.LinearLayout
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.MainFriendsFragment.Friend

import scala.collection.mutable.ListBuffer

class NewMessageActivity extends AppCompatActivity {

  var mToolbar: Toolbar = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_message)

    val colour : Int  = Color.parseColor("#F5A623")

    mToolbar = findViewById(R.id.newMessage_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(colour)
    findViewById(R.id.newMessage_appbar).asInstanceOf[LinearLayout].setBackgroundColor(colour)

    val window : Window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.setStatusBarColor(Color.parseColor("#8C5F14"));

    setSupportActionBar(mToolbar)
    getSupportActionBar.setTitle("Add friends to conversation")
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    val mFriends_Recycler : RecyclerView = findViewById(R.id.newmessage_recycler).asInstanceOf[RecyclerView];
    val mLayoutManager : LayoutManager = new LinearLayoutManager(this)

    var a = ListBuffer[Friend]()


    a += new Friend("Lorem Ipsum","Trying to TokTok",0,Color.parseColor("#DC4254"),Color.parseColor("#7D2530"),R.drawable.lorem)
    a+= new Friend("John Doe","Up!",0,Color.parseColor("#009688"),Color.parseColor("#00796B"),R.drawable.john)


    val mFriends_Recycler_Adapter : NewMessageRecyclerHeaderAdapter = new NewMessageRecyclerHeaderAdapter(a)
    mFriends_Recycler.setAdapter(mFriends_Recycler_Adapter)
    mFriends_Recycler.setLayoutManager(mLayoutManager)
    mFriends_Recycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter))

  }
}
