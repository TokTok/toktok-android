package im.tox.toktok.app.CallActivity

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.{Color, Point}
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.Animation.AnimationListener
import android.view.animation.{Animation, AnimationUtils}
import android.view.{Display, View}
import android.widget._
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.R
import im.tox.toktok.app.Friend
import im.tox.toktok.app.VideoCallActivity.VideoCallActivity

import scala.collection.mutable.ListBuffer


class CallActivity extends AppCompatActivity {

  var mTopPainel: RelativeLayout = null
  var mBottomPainel: FrameLayout = null
  var viewType: Int = 2
  var bundle : Bundle = null
  var midHeight: Double = 0
  var friendTitle: String = ""
  var friendColor: Int = 0
  var friendImgSRC: Int = 0
  var firstTime: Boolean = true
  var mFriendPhotoBig : CircleImageView = null
  var mCallAnswer : CallSliderAnswer = null
  var mCallDecline : CallSliderDecline = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_call_layout)

    bundle = getIntent.getExtras

    friendTitle = bundle.getString("contactName")
    friendColor = bundle.getInt("contactColorPrimary")
    friendImgSRC = bundle.getInt("contactPhotoReference")

    getWindow.getDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    getWindow.setStatusBarColor(getResources.getColor(R.color.contactsTransparentBar))

    mTopPainel = findViewById(R.id.call_top_painel).asInstanceOf[RelativeLayout]
    mBottomPainel = findViewById(R.id.call_bottom_painel).asInstanceOf[FrameLayout]
    mBottomPainel.setBackgroundColor(Color.argb(165, Color.red(friendColor), Color.green(friendColor), Color.blue(friendColor)))

    initBackground(friendImgSRC)

    if (viewType == 0 || viewType == 2) {
      initReceiveCall()
    }
    else if (viewType == 1) {
      initOnGoingCall
    }

  }


  def initOnGoingCall: Unit = {
    mTopPainel.setBackgroundColor(getResources.getColor(R.color.callTopColor))
    mTopPainel.addView(getLayoutInflater.inflate(R.layout.call_top_on_going, null))
    mBottomPainel.addView(getLayoutInflater.inflate(R.layout.call_bottom_on_going, null))


    val mRecycler: RecyclerView = findViewById(R.id.call_ongoing_contacts).asInstanceOf[RecyclerView]

    val list: ListBuffer[Friend] = new ListBuffer[Friend]
    list += new Friend(-1,friendTitle, "Trying the TokTok", 0, Color.parseColor("#DC4254"), Color.parseColor("#7D2530"), friendImgSRC)

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

    mTopPainel.addView(getLayoutInflater.inflate(R.layout.call_top_receive, null))
    mBottomPainel.addView(getLayoutInflater.inflate(R.layout.call_bottom_receive, null))

    mFriendPhotoBig = findViewById(R.id.call_img).asInstanceOf[CircleImageView]
    mFriendPhotoBig.setImageResource(friendImgSRC)

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

    mCallAnswer = findViewById(R.id.call_answer).asInstanceOf[CallSliderAnswer]
    mCallDecline = findViewById(R.id.call_decline).asInstanceOf[CallSliderDecline]

    mCallAnswer.setOnCallListener(new CallListener {
      override def onCompleted: Unit = {

        if(viewType == 0) {

          val fadeOutAnimationBottom = AnimationUtils.loadAnimation(mBottomPainel.getContext, R.anim.abc_fade_out)
          fadeOutAnimationBottom.setDuration(250)
          fadeOutAnimationBottom.setAnimationListener(new AnimationListener {

            override def onAnimationEnd(animation: Animation): Unit = {
              mBottomPainel.removeAllViews()
            }

            override def onAnimationStart(animation: Animation): Unit = {

            }

            override def onAnimationRepeat(animation: Animation): Unit = {}

          })

          mBottomPainel.getChildAt(0).startAnimation(fadeOutAnimationBottom)

          val fadeOutAnimationTop = AnimationUtils.loadAnimation(mBottomPainel.getContext, R.anim.abc_fade_out)
          fadeOutAnimationTop.setDuration(250)
          fadeOutAnimationTop.setAnimationListener(new AnimationListener {

            override def onAnimationEnd(animation: Animation): Unit = {
              mTopPainel.removeAllViews()
              initOnGoingCall
            }

            override def onAnimationStart(animation: Animation): Unit = {

            }

            override def onAnimationRepeat(animation: Animation): Unit = {}

          })

          mTopPainel.getChildAt(0).startAnimation(fadeOutAnimationTop)

        }
        else if(viewType == 2){

          val videoIntent = new Intent(CallActivity.this, classOf[VideoCallActivity])
          videoIntent.putExtras(bundle)
          startActivity(videoIntent)
          overridePendingTransition(R.anim.activity_fade_in,R.anim.activity_fade_out)
          finish

        }
      }

      override def onStart(): Unit ={
        val animation = AnimationUtils.loadAnimation(mCallDecline.getContext,R.anim.abc_fade_out)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            mCallDecline.setVisibility(View.INVISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        mCallDecline.startAnimation(animation)
      }

      override def onReleased(): Unit ={
        val animation = AnimationUtils.loadAnimation(mCallDecline.getContext,R.anim.abc_fade_in)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            mCallDecline.setVisibility(View.VISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        mCallDecline.startAnimation(animation)
      }

    })

    mCallDecline.setOnCallListener(new CallListener {

      override def onCompleted: Unit = {
        finish()
      }

      override def onReleased: Unit = {
        val animation = AnimationUtils.loadAnimation(mCallAnswer.getContext,R.anim.abc_fade_in)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            mCallAnswer.setVisibility(View.VISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        mCallAnswer.startAnimation(animation)
      }

      override def onStart: Unit = {
        val animation = AnimationUtils.loadAnimation(mCallAnswer.getContext,R.anim.abc_fade_out)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            mCallAnswer.setVisibility(View.INVISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        mCallAnswer.startAnimation(animation)
      }
    })

  }

  def initBackground(imgResource: Int): Unit = {

    val background = findViewById(R.id.call_background).asInstanceOf[ImageView]
    background.setImageResource(imgResource)

    val screen: Display = getWindowManager().getDefaultDisplay()
    val screenSize: Point = new Point()
    screen.getSize(screenSize)

    val content: View = findViewById(android.R.id.content).getRootView

    val thread = new Thread() {
      override def run(): Unit = {

        content.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener {
          override def onGlobalLayout(): Unit = {
            if (firstTime) {
              val picture = BlurBuilder.blur(background)
              background.setImageDrawable(new BitmapDrawable(getResources, picture))
              firstTime = false
            }
          }
        })

      }
    }


    thread.start()

    midHeight = screenSize.y * 0.5

    val params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, midHeight.toInt)
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    mBottomPainel.setLayoutParams(params)
    mTopPainel.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, midHeight.toInt))

  }

  override def onBackPressed(): Unit = {

  }

}

trait CallListener {
  def onCompleted
  def onStart
  def onReleased
}