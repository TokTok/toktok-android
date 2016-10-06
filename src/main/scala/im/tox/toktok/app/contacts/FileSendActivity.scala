package im.tox.toktok.app.contacts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{ LinearLayoutManager, RecyclerView, Toolbar }
import android.view.MenuItem
import im.tox.toktok.TypedBundleKey._
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.domain.File
import im.tox.toktok.{ BundleKey, R, TR }

import scala.collection.mutable.ListBuffer

final class FileSendActivity extends AppCompatActivity {

  private var mToolbar: Toolbar = null
  private var mRecycler: RecyclerView = null
  private var colorPrimary = 0
  private var colorStatus = 0
  private var userName = ""

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_files_send)

    val bundle = getIntent.getExtras

    colorPrimary = bundle(BundleKey.contactColorPrimary)
    colorStatus = bundle(BundleKey.contactColorStatus)
    userName = bundle(BundleKey.contactName)

    getWindow.setStatusBarColor(colorStatus)

    mToolbar = this.findView(TR.files_send_toolbar)
    mToolbar.setBackgroundColor(colorPrimary)
    mToolbar.setTitle(getResources.getString(R.string.files_send_title) + " " + userName)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)

    mRecycler = this.findView(TR.files_send_recycler)
    val list = ListBuffer(File.file)

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new FileSendActivityAdapter(list))
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    item.getItemId match {
      case android.R.id.home =>
        finish()
        true

      case _ =>
        super.onOptionsItemSelected(item)
    }
  }

}
