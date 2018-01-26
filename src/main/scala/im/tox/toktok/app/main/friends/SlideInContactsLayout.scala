package im.tox.toktok.app.main.friends

import android.app.Activity
import android.content.{ Context, Intent }
import android.content.res.ColorStateList
import android.graphics.drawable.TransitionDrawable
import android.os.Handler
import android.support.design.widget.{ CollapsingToolbarLayout, FloatingActionButton, Snackbar }
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.support.v7.widget.{ CardView, Toolbar }
import android.util.AttributeSet
import android.view.View.MeasureSpec
import android.view.animation.{ Animation, AnimationUtils }
import android.view.{ MotionEvent, View, ViewGroup }
import android.widget.{ ImageView, RelativeLayout, TextView }
import com.typesafe.scalalogging.Logger
import im.tox.toktok.TypedBundleKey._
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.call.CallActivity
import im.tox.toktok.app.contacts.FileSendActivity
import im.tox.toktok.app.domain.Friend
import im.tox.toktok.app.message_activity.{ MessageActivity, SupportDesignR }
import im.tox.toktok.app.simple_dialogs.{ SimpleColorDialogDesign, SimpleDialogDesign, SimpleTextDialogDesign }
import im.tox.toktok.app.video_call.VideoCallActivity
import im.tox.toktok.{ BundleKey, R, TR }
import org.slf4j.LoggerFactory

final class SlideInContactsLayout(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int
) extends ViewGroup(
  context,
  attrs,
  defStyle
) {

  private val logger = Logger(LoggerFactory.getLogger(getClass))

  private implicit var activity: Activity = null

  private val mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback)
  private var mCoordinator: View = null
  private var mCollapsingToolbarLayout: CollapsingToolbarLayout = null
  private var mFloatingActionButton: FloatingActionButton = null
  private var mUserImage: ImageView = null
  private var mSubtitle: TextView = null
  private var mTitle: TextView = null
  private var mSettingsTitle: TextView = null
  private var mToolbar: Toolbar = null
  private var mStatusBar: View = null
  private var mEditNameButton: RelativeLayout = null
  private var friend: Friend = null
  private var mInitialMotionY = .0
  private var mDragRange = 0
  private var mTop = 0
  private var scrollActive = false
  private var mDragOffset = .0
  private var backgroundTransition: TransitionDrawable = null
  private var mVoiceCall: TextView = null
  private var mVideoCall: TextView = null
  private var mMessage: CardView = null
  private var mSaveProfile: CardView = null
  private var mFilesSend: CardView = null
  private var mDeleteFriend: RelativeLayout = null
  private var mBlockFriend: RelativeLayout = null
  private var mChangeColor: RelativeLayout = null
  private var scrollTop = 0

  private val icons = Array(
    TR.contacts_icon_call,
    TR.contacts_icon_message,
    TR.contacts_icon_image,
    TR.contacts_icon_download,
    TR.contacts_icon_palette,
    TR.contacts_icon_edit,
    TR.contacts_icon_trash,
    TR.contacts_icon_lock
  )

  def this(context: Context, attrs: AttributeSet) { this(context, attrs, 0) }
  def this(context: Context) { this(context, null) }

  protected override def onFinishInflate(): Unit = {
    mCoordinator = this.findView(TR.contacts_coordinator_layout)
    mCollapsingToolbarLayout = this.findView(TR.contacts_collapsing_toolbar)
    mFloatingActionButton = this.findView(TR.contacts_FAB)
    mUserImage = this.findView(TR.contact_image)
    mTitle = this.findView(TR.contact_title)
    mSubtitle = this.findView(TR.contact_subtitle)
    mSettingsTitle = this.findView(TR.contacts_other_title)
    mToolbar = this.findView(TR.contacts_toolbar)
    mVoiceCall = this.findView(TR.contacts_item_voice_call)
    mVideoCall = this.findView(TR.contacts_item_video_call)
    mEditNameButton = this.findView(TR.contacts_edit_alias)
    mStatusBar = this.findView(TR.contacts_status_bar_color)
    mStatusBar.getLayoutParams.height = getStatusBarHeight
    mMessage = this.findView(TR.contacts_message)
    mSaveProfile = this.findView(TR.contacts_save_photo)
    mFilesSend = this.findView(TR.contacts_file_download)
    mDeleteFriend = this.findView(TR.contacts_delete)
    mBlockFriend = this.findView(TR.contacts_block_friend)
    mChangeColor = this.findView(TR.contacts_edit_color)
    super.onFinishInflate()
  }

  def start(activity: Activity, friend: Friend, actionBarHeight: Int): Unit = {
    this.activity = activity
    this.friend = friend
    mTitle.setText(friend.userName)

    mCollapsingToolbarLayout.setBackgroundColor(friend.color)
    mCollapsingToolbarLayout.setContentScrimColor(friend.color)
    mCollapsingToolbarLayout.setStatusBarScrimColor(friend.secondColor)

    mUserImage.setImageResource(friend.photoReference)

    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(friend.color))

    mSubtitle.setText(friend.userMessage)

    mSettingsTitle.setTextColor(ColorStateList.valueOf(friend.color))

    val b = mToolbar.getLayoutParams.asInstanceOf[CollapsingToolbarLayout.LayoutParams]
    b.height = actionBarHeight + getStatusBarHeight
    mToolbar.setLayoutParams(b)
    mToolbar.setPadding(0, getStatusBarHeight, 0, 0)

    for (item <- icons) {
      val icon = this.findView(item)
      icon.setImageTintList(ColorStateList.valueOf(friend.color))
    }

    initListeners(friend)
    setVisibility(View.VISIBLE)

    backgroundTransition = getBackground.asInstanceOf[TransitionDrawable]
    backgroundTransition.startTransition(500)

    val animation = AnimationUtils.loadAnimation(getContext, R.anim.slide_in_bottom)
    animation.setAnimationListener(new Animation.AnimationListener() {
      def onAnimationStart(animation: Animation): Unit = {}

      def onAnimationEnd(animation: Animation): Unit = {
        mCoordinator.setVisibility(View.VISIBLE)
      }

      def onAnimationRepeat(animation: Animation): Unit = {}
    })
    mCoordinator.startAnimation(animation)
  }

  private def smoothSlideTo(slideOffset: Float): Boolean = {
    val topBound = getPaddingTop
    val y = (topBound + slideOffset * mDragRange).toInt
    if (mDragHelper.smoothSlideViewTo(mCoordinator, mCoordinator.getLeft, y)) {
      ViewCompat.postInvalidateOnAnimation(this)
      true
    } else {
      false
    }
  }

  override def computeScroll(): Unit = {
    if (mDragHelper.continueSettling(true)) {
      ViewCompat.postInvalidateOnAnimation(this)
    }
  }

  override def onInterceptTouchEvent(ev: MotionEvent): Boolean = {
    if (scrollActive) {
      mDragHelper.cancel()
      return false
    }

    ev.getAction match {
      case MotionEvent.ACTION_DOWN =>
        logger.debug("Intercept Touch DOWN")
      case MotionEvent.ACTION_MOVE =>
        logger.debug("Intercept Touch MOVE")
      case MotionEvent.ACTION_CANCEL | MotionEvent.ACTION_UP =>
        logger.debug("Intercept Touch UP")
    }

    mDragHelper.shouldInterceptTouchEvent(ev)
  }

  override def onTouchEvent(ev: MotionEvent): Boolean = {
    try {
      mDragHelper.processTouchEvent(ev)
      true
    } catch {
      case ex: Exception =>
        false
    }
  }

  override def dispatchTouchEvent(ev: MotionEvent): Boolean = {
    val y = ev.getY
    val v = this.findView(TR.contacts_nested)

    ev.getAction match {
      case MotionEvent.ACTION_DOWN =>
        mInitialMotionY = y
      case MotionEvent.ACTION_MOVE =>
        val dy = mInitialMotionY - y
      case MotionEvent.ACTION_UP =>
        val dy = mInitialMotionY - y
        if (dy > 0) {
          if (mDragOffset < 0.5 && !scrollActive) {
            smoothSlideTo(0)
            scrollActive = true
            mStatusBar.setVisibility(View.VISIBLE)
            mStatusBar.bringToFront()
            scrollTop = v.getBottom
          }
        } else {
          if (!scrollActive && Math.abs(dy) > 20) {
            if (mDragOffset > 0.5f) {
              finish()
            } else {
              smoothSlideTo(0.5f)
              mStatusBar.setVisibility(View.INVISIBLE)
            }
          } else {
            if (v.getBottom >= scrollTop) {
              scrollActive = false
            }
          }
        }
    }
    super.dispatchTouchEvent(ev)
  }

  protected override def onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int): Unit = {
    measureChildren(widthMeasureSpec, heightMeasureSpec)
    val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
    val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
    setMeasuredDimension(
      View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
      View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
    )
  }

  protected override def onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int): Unit = {
    mDragRange = getHeight
    if (changed) {
      mTop = getHeight / 2
      mCoordinator.layout(0, getHeight / 2, right, mTop + mCoordinator.getMeasuredHeight)
    } else {
      mCoordinator.layout(0, mTop, right, mTop + mCoordinator.getMeasuredHeight)
    }
  }

  def finish(after: => Unit = {}): Unit = {
    smoothSlideTo(1f)
    backgroundTransition.reverseTransition(500)
    new Handler().postDelayed(new Runnable() {
      def run(): Unit = {
        mCoordinator.setVisibility(View.INVISIBLE)
        setVisibility(View.GONE)
        after
      }
    }, 500)
  }

  private final class DragHelperCallback extends ViewDragHelper.Callback {
    def tryCaptureView(child: View, pointerId: Int): Boolean = {
      child == mCoordinator
    }

    override def onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int): Unit = {
      mTop = top
      mDragOffset = top.toFloat / mDragRange
      requestLayout()
    }

    override def onViewReleased(releasedChild: View, xvel: Float, yvel: Float): Unit = {
      var top = getPaddingTop
      if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
        top += mDragRange
      }
    }

    override def getViewVerticalDragRange(child: View): Int = {
      mDragRange
    }

    override def clampViewPositionVertical(child: View, top: Int, dy: Int): Int = {
      val topBound = 0
      val bottomBound = getHeight
      Math.min(Math.max(top, topBound), bottomBound)
    }
  }

  private def initListeners(friend: Friend): Unit = {
    mEditNameButton.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleTextDialogDesign(activity, getResources.getString(R.string.contact_popup_edit_alias), friend.color, R.drawable.ic_person_black_48dp, friend.userName, null)
        dial.show()
      }
    })

    mVoiceCall.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        activity.startActivity(new Intent(activity, classOf[CallActivity]).putExtras(SBundle(
          BundleKey.contactName -> friend.userName,
          BundleKey.contactColorPrimary -> friend.color,
          BundleKey.contactPhotoReference -> friend.photoReference
        )))
      }
    })

    mVideoCall.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        activity.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
        activity.startActivity(new Intent(activity, classOf[VideoCallActivity]).putExtras(SBundle(
          BundleKey.contactPhotoReference -> friend.photoReference
        )))
      }
    })

    mMessage.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        activity.startActivity(new Intent(activity, classOf[MessageActivity]).putExtras(SBundle(
          BundleKey.messageTitle -> friend.userName,
          BundleKey.contactColorPrimary -> friend.color,
          BundleKey.contactColorStatus -> friend.secondColor,
          BundleKey.imgResource -> friend.photoReference,
          BundleKey.messageType -> 0
        )))
      }
    })

    mSaveProfile.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val snack = Snackbar.make(mCoordinator, getResources.getString(R.string.contact_save_photo_snackbar), Snackbar.LENGTH_LONG)
        val snackView = snack.getView
        snackView.setBackgroundResource(R.color.snackBarColor)
        val snackText = snackView.findViewById(SupportDesignR.snackbar_text).asInstanceOf[TextView]
        snackText.setTextColor(getResources.getColor(R.color.textDarkColor, null))
        snack.show()
      }
    })

    mFilesSend.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        activity.startActivity(new Intent(activity, classOf[FileSendActivity]).putExtras(SBundle(
          BundleKey.contactName -> friend.userName,
          BundleKey.contactColorPrimary -> friend.color,
          BundleKey.contactColorStatus -> friend.secondColor
        )))
      }
    })

    mDeleteFriend.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleDialogDesign(
          activity,
          getResources.getString(R.string.dialog_delete_friend) + " " +
            friend.userName + " " +
            getResources.getString(R.string.dialog_delete_friend_end),
          friend.color, R.drawable.ic_person_black_48dp, null
        )
        dial.show()
      }
    })

    mBlockFriend.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val snack = Snackbar.make(mCoordinator, getResources.getString(R.string.contact_blocked), Snackbar.LENGTH_LONG)
        val snackView = snack.getView
        snackView.setBackgroundResource(R.color.snackBarColor)
        val snackText = snackView.findViewById(SupportDesignR.snackbar_text).asInstanceOf[TextView]
        snackText.setTextColor(getResources.getColor(R.color.textDarkColor, null))
        snack.show()
      }
    })

    mChangeColor.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleColorDialogDesign(
          activity,
          getResources.getString(R.string.dialog_change_color) + " " +
            friend.userName + " " +
            getResources.getString(R.string.dialog_change_color_end),
          friend.color, R.drawable.ic_image_color_lens, 0, null
        )
        dial.show()
      }
    })
  }

  def getStatusBarHeight: Int = {
    val resourceId = getResources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
      getResources.getDimensionPixelSize(resourceId)
    } else {
      0
    }
  }
}
