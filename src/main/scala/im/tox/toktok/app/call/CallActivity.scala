package im.tox.toktok.app.call

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.{ Color, Point }
import android.os.Bundle
import android.support.v7.widget.{ LinearLayoutManager, RecyclerView }
import android.view.{ Display, View }
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.Animation.AnimationListener
import android.view.animation.{ Animation, AnimationUtils }
import android.widget._
import com.typesafe.scalalogging.Logger
import de.hdodenhof.circleimageview.CircleImageView
import im.tox.toktok.TypedBundleKey._
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.domain.Friend
import im.tox.toktok.app.util.ActivityAdapter
import im.tox.toktok.app.video_call.VideoCallActivity
import im.tox.toktok.{ BundleKey, R, TR }
import org.slf4j.LoggerFactory

final class CallActivity extends ActivityAdapter[CallActivityViewHolder](TR.layout.activity_call_layout) {

  private val logger = Logger(LoggerFactory.getLogger(getClass))

  private val viewType: Int = 2

  private var backgroundInitialised: Boolean = false

  protected override def onCreateViewHolder(): CallActivityViewHolder = {
    new CallActivityViewHolder(this)
  }

  protected override def onCreate(holder: CallActivityViewHolder): Unit = {
    getWindow.getDecorView.setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    )
    getWindow.setStatusBarColor(getResources.getColor(R.color.contactsTransparentBar, null))

    holder.bottomPanel.setBackgroundColor(Color.argb(
      165,
      Color.red(holder.friendColor),
      Color.green(holder.friendColor),
      Color.blue(holder.friendColor)
    ))

    initBackground(holder, holder.friendImgSrc)

    viewType match {
      case 0 | 2 =>
        initReceiveCall(holder)
      case 1 =>
        initOnGoingCall(holder)
    }
  }

  private def initOnGoingCall(holder: CallActivityViewHolder): Unit = {
    holder.topPanel.setBackgroundColor(getResources.getColor(R.color.callTopColor, null))
    holder.topPanel.addView(getLayoutInflater.inflate(TR.layout.call_top_on_going))
    holder.bottomPanel.addView(getLayoutInflater.inflate(TR.layout.call_bottom_on_going))

    val contactsView: RecyclerView = this.findView(TR.call_ongoing_contacts)

    val friends: Seq[Friend] = Seq(
      Friend(
        -1,
        holder.friendTitle,
        "Trying the TokTok",
        0,
        Color.parseColor("#DC4254"),
        Color.parseColor("#7D2530"),
        holder.friendImgSrc
      )
    )

    val contactsLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    contactsView.setLayoutManager(contactsLayoutManager)
    contactsView.setAdapter(new CallOnGoingContactsAdapter(friends))

    this.findView(TR.call_ongoing_fab).setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        finish()
      }
    })
  }

  private def initReceiveCall(holder: CallActivityViewHolder): Unit = {
    holder.topPanel.addView(getLayoutInflater.inflate(TR.layout.call_top_receive))
    holder.bottomPanel.addView(getLayoutInflater.inflate(TR.layout.call_bottom_receive))

    val friendPhotoView: CircleImageView = this.findView(TR.call_img)
    friendPhotoView.setImageResource(holder.friendImgSrc)

    this.findView(TR.call_friend).setText(holder.friendTitle)
    this.findView(TR.call_message_input).setHint(getResources.getString(R.string.call_input_message) + " " + holder.friendTitle)

    val callMessageView: RecyclerView = this.findView(TR.call_messages_recycler)
    val excuses: Seq[String] = Seq(
      "Sorry I’m In Class, Call you later",
      "I’m at a meeting, can’t talk",
      "Call you later",
      "I’m driving, call you when I stop",
      "Not available"
    )

    val callMessageLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    callMessageView.setLayoutManager(callMessageLayoutManager)
    callMessageView.setAdapter(new CallMessageAdapter(excuses))

    val callAnswerSlider: CallSliderAnswer = this.findView(TR.call_answer)
    val callDeclineSlider: CallSliderDecline = this.findView(TR.call_decline)

    callAnswerSlider.setOnCallListener(new CallListener {
      override def onCompleted(): Unit = {
        viewType match {
          case 0 =>
            val fadeOutAnimationBottom = AnimationUtils.loadAnimation(holder.bottomPanel.getContext, R.anim.abc_fade_out)
            fadeOutAnimationBottom.setDuration(250)
            fadeOutAnimationBottom.setAnimationListener(new AnimationListener {
              override def onAnimationEnd(animation: Animation): Unit = {
                holder.bottomPanel.removeAllViews()
              }

              override def onAnimationStart(animation: Animation): Unit = {
              }

              override def onAnimationRepeat(animation: Animation): Unit = {
              }
            })

            holder.bottomPanel.getChildAt(0).startAnimation(fadeOutAnimationBottom)

            val fadeOutAnimationTop = AnimationUtils.loadAnimation(holder.bottomPanel.getContext, R.anim.abc_fade_out)
            fadeOutAnimationTop.setDuration(250)
            fadeOutAnimationTop.setAnimationListener(new AnimationListener {
              override def onAnimationEnd(animation: Animation): Unit = {
                holder.topPanel.removeAllViews()
                initOnGoingCall(holder)
              }

              override def onAnimationStart(animation: Animation): Unit = {
              }

              override def onAnimationRepeat(animation: Animation): Unit = {
              }
            })

            holder.topPanel.getChildAt(0).startAnimation(fadeOutAnimationTop)

          case 2 =>
            startActivity(new Intent(CallActivity.this, classOf[VideoCallActivity]).putExtras(holder.bundle))
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
            finish()
        }
      }

      override def onStart(): Unit = {
        val animation: Animation = AnimationUtils.loadAnimation(callDeclineSlider.getContext, R.anim.abc_fade_out)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            callDeclineSlider.setVisibility(View.INVISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        callDeclineSlider.startAnimation(animation)
      }

      override def onReleased(): Unit = {
        val animation: Animation = AnimationUtils.loadAnimation(callDeclineSlider.getContext, R.anim.abc_fade_in)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            callDeclineSlider.setVisibility(View.VISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        callDeclineSlider.startAnimation(animation)
      }

    })

    callDeclineSlider.setOnCallListener(new CallListener {

      override def onCompleted(): Unit = {
        finish()
      }

      override def onReleased(): Unit = {
        val animation: Animation = AnimationUtils.loadAnimation(callAnswerSlider.getContext, R.anim.abc_fade_in)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            callAnswerSlider.setVisibility(View.VISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        callAnswerSlider.startAnimation(animation)
      }

      override def onStart(): Unit = {
        val animation: Animation = AnimationUtils.loadAnimation(callAnswerSlider.getContext, R.anim.abc_fade_out)
        animation.setDuration(250)
        animation.setAnimationListener(new AnimationListener {
          override def onAnimationEnd(animation: Animation): Unit = {
            callAnswerSlider.setVisibility(View.INVISIBLE)
          }

          override def onAnimationStart(animation: Animation): Unit = {}

          override def onAnimationRepeat(animation: Animation): Unit = {}
        })
        callAnswerSlider.startAnimation(animation)
      }
    })

    val bottomTextBar: TextView = this.findView(TR.call_message_bottom_bar)

    bottomTextBar.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        logger.debug("asda")
      }
    })
  }

  private def initBackground(holder: CallActivityViewHolder, imgResource: Int): Unit = {
    val background: ImageView = this.findView(TR.call_background)
    background.setImageResource(imgResource)

    val screen: Display = getWindowManager.getDefaultDisplay
    val screenSize: Point = new Point()
    screen.getSize(screenSize)

    val content: View = findViewById[View](android.R.id.content).getRootView

    content.getViewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener {
      override def onGlobalLayout(): Unit = {
        if (!backgroundInitialised) {
          val picture = BlurBuilder.blur(background)
          background.setImageDrawable(new BitmapDrawable(getResources, picture))
          backgroundInitialised = true
        }
      }
    })

    val midHeight = screenSize.y * 0.5

    val params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, midHeight.toInt)
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    holder.bottomPanel.setLayoutParams(params)
    holder.topPanel.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, midHeight.toInt))
  }

  override def onBackPressed(): Unit = {}

}

final class CallActivityViewHolder(activity: CallActivity) {
  val topPanel: RelativeLayout = activity.findView(TR.call_top_panel)
  val bottomPanel: FrameLayout = activity.findView(TR.call_bottom_panel)

  val bundle: Bundle = activity.getIntent.getExtras
  val friendTitle: String = bundle(BundleKey.contactName)
  val friendColor: Int = bundle(BundleKey.contactColorPrimary)
  val friendImgSrc: Int = bundle(BundleKey.contactPhotoReference)
}

trait CallListener {
  def onCompleted(): Unit
  def onStart(): Unit
  def onReleased(): Unit
}
