package im.tox.toktok.app.ContactsActivity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import im.tox.toktok.R
import im.tox.toktok.app.SimpleDialogs.FileSendActivityAdapter

import scala.collection.mutable.ListBuffer


class FileSendActivity extends AppCompatActivity{

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_files_send)

    val mToolbar : Toolbar = findViewById(R.id.files_send_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(Color.parseColor("#DC4254"))
    mToolbar.setTitle("Files send by Lorem")
    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)


    val mRecycler : RecyclerView = findViewById(R.id.files_send_recycler).asInstanceOf[RecyclerView]
    val list : ListBuffer[File] = new ListBuffer[File]
    list+=(new File("Movie_2015-02-01.mp4","Downloaded 2015-05-21"))

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new FileSendActivityAdapter(list))

  }
}