package im.tox.toktok.app.ContactsActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.util.Log
import android.view.MenuItem
import im.tox.toktok.R
import im.tox.toktok.app.File
import im.tox.toktok.app.SimpleDialogs.FileSendActivityAdapter

import scala.collection.mutable.ListBuffer


class FileSendActivity extends AppCompatActivity {

  var mToolbar: Toolbar = null
  var mRecycler: RecyclerView = null
  var colorPrimary: Int = 0
  var colorStatus: Int = 0
  var userName: String = ""

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_files_send)

    val bundle: Bundle = getIntent.getExtras

    if (bundle == null) {
      Log.d("asd", "asda")
    }

    colorPrimary = bundle.getInt("contactColorPrimary")
    colorStatus = bundle.getInt("contactColorSecondary")
    userName = bundle.getString("contactName")

    getWindow.setStatusBarColor(colorStatus)

    mToolbar = findViewById(R.id.files_send_toolbar).asInstanceOf[Toolbar]
    mToolbar.setBackgroundColor(colorPrimary)
    mToolbar.setTitle(getResources.getString(R.string.files_send_title) + " " + userName)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)


    mRecycler = findViewById(R.id.files_send_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[File] = ListBuffer(File.file)

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new FileSendActivityAdapter(list))

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