package im.tox.toktok.app.MessageActivity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.util.Log
import android.view.MenuItem
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import com.tonicartos.superslim.LayoutManager
import im.tox.toktok.R
import im.tox.toktok.app.Message

import scala.collection.mutable.ListBuffer

class MessageRecallActivity extends AppCompatActivity with RecallMessageListener {

  var colorPrimary: Int = 0
  var colorStatus: Int = 0
  var adapter : MessageRecallRecyclerAdapter = null
  var mToolbar : Toolbar = null
  var mFAB : FloatingActionButton = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recall_message)

    val bundle: Bundle = getIntent.getExtras

    colorPrimary = bundle.getInt("colorPrimary")
    colorStatus = bundle.getInt("colorPrimaryStatus")

    mToolbar = findViewById(R.id.recall_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(colorPrimary)
    getWindow.setStatusBarColor(colorStatus)

    mFAB = findViewById(R.id.recall_fab).asInstanceOf[FloatingActionButton]

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)


    val mRecycler: RecyclerView = findViewById(R.id.recall_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[Message] = new ListBuffer[Message]

    var a = 0

    list += (new Message(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "23th of April", R.drawable.lorem))


    for(a <- 0 to 10 ){

      list += (new Message(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "24th of April", R.drawable.lorem))

    }


    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(new LayoutManager(this))

    adapter = new MessageRecallRecyclerAdapter(this,list,this)



    mRecycler.setAdapter(adapter)

  }

  def onClick(position : Int): Unit ={
    adapter.toggleSelection(position)

    val i = adapter.getSelectedCount

    if(i == 0){
      getSupportActionBar.setTitle("Recall Messages")
      mFAB.hide()
    }
    else if(i == 1){
      getSupportActionBar.setTitle(adapter.getSelectedCount()+" selected message")
      mFAB.show()
    }
    else{
      getSupportActionBar.setTitle(adapter.getSelectedCount()+" selected messages")
    }

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