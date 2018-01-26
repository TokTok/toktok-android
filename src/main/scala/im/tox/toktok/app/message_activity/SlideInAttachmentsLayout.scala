package im.tox.toktok.app.message_activity

import android.content.Context
import android.os.Handler
import android.support.v4.view.{ MotionEventCompat, ViewCompat }
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.View.MeasureSpec
import android.view.{ MotionEvent, View, ViewGroup }
import com.typesafe.scalalogging.Logger
import im.tox.toktok.TR
import im.tox.toktok.TypedResource._
import org.slf4j.LoggerFactory

final class SlideInAttachmentsLayout(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int
) extends ViewGroup(context, attrs, defStyle) {

  private val logger = Logger(LoggerFactory.getLogger(getClass))

  private val mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback)
  private var mChild: View = null
  private var mInitialMotionY = 0.0f
  private var mDragRange = 0
  private var mTop = 0
  private var mDragOffset = 0.0f

  def this(context: Context, attrs: AttributeSet) { this(context, attrs, 0) }
  def this(context: Context) { this(context, null) }

  protected override def onFinishInflate(): Unit = {
    mChild = this.findView(TR.fragment_attachments)
    super.onFinishInflate()
  }

  private def smoothSlideTo(slideOffset: Float): Boolean = {
    val topBound = getHeight - mChild.getHeight
    val y = (topBound + slideOffset * mDragRange).toInt
    if (mDragHelper.smoothSlideViewTo(mChild, mChild.getLeft, y)) {
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
    val y = ev.getY

    ev.getActionMasked match {
      case MotionEvent.ACTION_DOWN =>
        logger.debug("DOWN")
        mInitialMotionY = y

      case MotionEvent.ACTION_UP =>
        logger.debug("UP")
        return true

      case _ =>
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

  def start(): Unit = {
    setVisibility(View.VISIBLE)
    smoothSlideTo(0f)
  }

  def finish(): Unit = {
    smoothSlideTo(1f)
    new Handler().postDelayed(new Runnable() {
      def run(): Unit = {
        setVisibility(View.INVISIBLE)
      }
    }, 500)
  }

  override def dispatchTouchEvent(ev: MotionEvent): Boolean = {
    val y = ev.getY

    ev.getActionMasked match {
      case MotionEvent.ACTION_DOWN =>
        mInitialMotionY = y
        if (y < getHeight - mChild.getHeight) {
          finish()
        }

      case MotionEvent.ACTION_UP =>
        val dy = y - mInitialMotionY
        if ((dy > 0 && (dy / mChild.getHeight) > 0.3) || dy == 0) {
          finish()
        } else {
          smoothSlideTo(0f)
        }

      case _ =>
    }

    super.dispatchTouchEvent(ev)
  }

  protected override def onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int): Unit = {
    measureChildren(widthMeasureSpec, heightMeasureSpec)
    val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
    val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
    setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0), View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0))
  }

  protected override def onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int): Unit = {
    mDragRange = mChild.getHeight
    if (changed) {
      mTop = getHeight - mChild.getHeight
      mChild.layout(0, getHeight - mChild.getHeight, r, getHeight)
    } else {
      mChild.layout(0, mTop, r, mTop + mChild.getMeasuredHeight)
    }
  }

  private final class DragHelperCallback extends ViewDragHelper.Callback {

    override def tryCaptureView(child: View, pointerId: Int): Boolean = {
      child == mChild
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
      val topBound = getHeight - mChild.getHeight
      val bottomBound = getHeight + mChild.getPaddingTop
      Math.min(Math.max(top, topBound), bottomBound)
    }

  }

}
