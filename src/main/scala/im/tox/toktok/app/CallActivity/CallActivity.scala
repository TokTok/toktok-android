package im.tox.toktok.app.CallActivity

import android.graphics.drawable.BitmapDrawable
import android.graphics.{Color, Point}
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.{Display, View}
import android.widget._
import com.github.siyamed.shapeimageview.CircularImageView
import im.tox.toktok.R
import im.tox.toktok.app.Friend

import scala.collection.mutable.ListBuffer


class CallActivity extends AppCompatActivity {

  var topPainel: RelativeLayout = null
  var bottomPainel: FrameLayout = null
  var viewType: Int = 1
  var midHeight: Double = 0
  var friendTitle: String = ""
  var friendColor: Int = 0
  var friendImgSRC: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_call_layout)

    val bundle: Bundle = getIntent.getExtras

    friendTitle = bundle.getString("contactName")
    friendColor = bundle.getInt("contactColorPrimary")
    friendImgSRC = bundle.getInt("contactPhotoReference")

    getWindow.getDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    getWindow.setStatusBarColor(getResources.getColor(R.color.contactsTransparentBar))

    topPainel = findViewById(R.id.call_top_painel).asInstanceOf[RelativeLayout]
    bottomPainel = findViewById(R.id.call_bottom_painel).asInstanceOf[FrameLayout]
    bottomPainel.setBackgroundColor(Color.argb(165, Color.red(friendColor), Color.green(friendColor), Color.blue(friendColor)))


    initBackground(friendImgSRC)

    if (viewType == 0) {
      initReceiveCall()
    }
    else if (viewType == 1) {
      initOnGoingCall
    }

  }


  def initOnGoingCall: Unit = {
    topPainel.setBackgroundColor(getResources.getColor(R.color.callTopColor))
    topPainel.addView(getLayoutInflater.inflate(R.layout.call_top_on_going, null))
    bottomPainel.addView(getLayoutInflater.inflate(R.layout.call_bottom_on_going, null))


    val mRecycler: RecyclerView = findViewById(R.id.call_ongoing_contacts).asInstanceOf[RecyclerView]

    val list: ListBuffer[Friend] = new ListBuffer[Friend]
    list += new Friend(friendTitle, "Trying the TokTok", 0, Color.parseColor("#DC4254"), Color.parseColor("#7D2530"), friendImgSRC)

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new CallOnGoingContactsAdapter(list))

    findViewById(R.id.call_ongoing_fab).asInstanceOf[FloatingActionButton].setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        finish()
      }
    })

  }


  def initReceiveCall(): Unit = {

    topPainel.addView(getLayoutInflater.inflate(R.layout.call_top_receive, null))
    bottomPainel.addView(getLayoutInflater.inflate(R.layout.call_bottom_receive, null))

    findViewById(R.id.call_img).asInstanceOf[CircularImageView].setImageResource(friendImgSRC)
    findViewById(R.id.call_friend).asInstanceOf[TextView].setText(friendTitle)
    findViewById(R.id.call_message_input).asInstanceOf[EditText].setHint(getResources.getString(R.string.call_input_message) + " " + friendTitle)


    val mRecycler: RecyclerView = findViewById(R.id.call_messages_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[String] = new ListBuffer[String]
    list += "Sorry I’m In Class, Call you later"
    list += "I’m at a metting, can’t talk"
    list += "Call you later"
    list += "I’m driving, call you when I stop"
    list += "Not available"

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new CallMessageAdapter(list))

    val declineSeekbar: SeekBar = findViewById(R.id.call_decline).asInstanceOf[SeekBar]
    declineSeekbar.setRotation(180);


  }

  def initBackground(imgResource: Int): Unit = {

    val background = findViewById(R.id.call_background).asInstanceOf[ImageView]
    background.setImageResource(imgResource)

    val screen: Display = getWindowManager().getDefaultDisplay()
    val screenSize: Point = new Point()
    screen.getSize(screenSize)

    val content: View = findViewById(android.R.id.content).getRootView

    content.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener {
      override def onGlobalLayout(): Unit = {
        val picture = BlurBuilder.blur(background)
        background.setImageDrawable(new BitmapDrawable(getResources, picture))

      }
    })
    midHeight = screenSize.y * 0.5

    val params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, midHeight.toInt)
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    bottomPainel.setLayoutParams(params)
    topPainel.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, midHeight.toInt))

  }

  override def onBackPressed(): Unit = {

  }

}