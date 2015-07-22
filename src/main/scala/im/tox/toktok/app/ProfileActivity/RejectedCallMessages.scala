package im.tox.toktok.app.ProfileActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer


class RejectedCallMessages extends AppCompatActivity {

  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_reject_call)

    val mToolbar = findViewById(R.id.reject_call_toolbar).asInstanceOf[Toolbar]
    mToolbar.setTitle("Reject call messages")
    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true);

    val mRecycler: RecyclerView = findViewById(R.id.reject_recycler).asInstanceOf[RecyclerView];
    val mLayoutManager: LayoutManager = new LinearLayoutManager(this)
    mRecycler.setLayoutManager(mLayoutManager)

    var a = ListBuffer[String]()

    a += "Sorry I’m In Class, Call you later"
    a += "I’m at a metting, can’t talk"

    val mAdapter: RejectedCallAdapter = new RejectedCallAdapter(a)
    mRecycler.setAdapter(mAdapter)

  }

}