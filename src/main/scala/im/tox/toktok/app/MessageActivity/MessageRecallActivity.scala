package im.tox.toktok.app.MessageActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer

class MessageRecallActivity extends AppCompatActivity {

  var colorPrimary: Int = 0
  var colorStatus: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recall_message)

    val bundle: Bundle = getIntent.getExtras

    colorPrimary = bundle.getInt("colorPrimary")
    colorStatus = bundle.getInt("colorPrimaryStatus")

    val mToolbar: Toolbar = findViewById(R.id.recall_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(colorPrimary)
    getWindow.setStatusBarColor(colorStatus)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)


    val mRecycler: RecyclerView = findViewById(R.id.recall_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[Message] = new ListBuffer[Message]
    list += (new Message(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "14:30 Delivered", R.drawable.lorem))

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    val adapter: MessageRecallRecyclerHeaderAdapter = new MessageRecallRecyclerHeaderAdapter(list)

    mRecycler.setAdapter(adapter)
    mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter))

  }

}