package im.tox.toktok.app.MessageActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.view.View
import android.view.View.OnClickListener
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R
import im.tox.toktok.app.Friend
import im.tox.toktok.app.MainActivity.MainFriendsFragment.FriendsRecyclerHeaderAdapter
import im.tox.toktok.app.NewMessageActivity.NewMessageActivity

import scala.collection.mutable.ListBuffer


class MessageGroupContacts extends AppCompatActivity {


  var colorPrimary: Int = 0
  var colorStatus: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_message_group_members)

    val bundle: Bundle = getIntent.getExtras

    colorPrimary = bundle.getInt("colorPrimary")
    colorStatus = bundle.getInt("colorPrimaryStatus")

    val mToolbar: Toolbar = findViewById(R.id.message_group_members_toolbar).asInstanceOf[Toolbar]
    mToolbar.setTitle("Contacts list")
    mToolbar.setBackgroundColor(colorPrimary)
    getWindow.setStatusBarColor(colorStatus)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)


    val mFAB: FloatingActionButton = findViewById(R.id.message_group_members_fab).asInstanceOf[FloatingActionButton]

    mFAB.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val addContactView: Intent = new Intent(MessageGroupContacts.this, classOf[NewMessageActivity])
        bundle.putInt("colorPrimary", colorPrimary)
        bundle.putInt("colorPrimaryStatus", colorStatus)
        addContactView.putExtras(bundle)
        startActivity(addContactView)

      }
    })

    val mRecycler: RecyclerView = findViewById(R.id.message_group_members_recycler).asInstanceOf[RecyclerView]

    val list: ListBuffer[Friend] = new ListBuffer[Friend]
    list += new Friend("Lorem Ipsum", "Trying the TokTok", 0, Color.parseColor("#DC4254"), Color.parseColor("#7D2530"), R.drawable.lorem)
    list += new Friend("John Doe", "Up!", 0, Color.parseColor("#009688"), Color.parseColor("#00796B"), R.drawable.john)

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    val adapter: FriendsRecyclerHeaderAdapter = new FriendsRecyclerHeaderAdapter(list)
    mRecycler.setAdapter(adapter)
    mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter))


  }

}