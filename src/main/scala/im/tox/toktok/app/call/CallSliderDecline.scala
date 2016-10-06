package im.tox.toktok.app.call

import android.content.Context
import android.util.AttributeSet
import android.view.{ LayoutInflater, MotionEvent, View }
import android.widget.RelativeLayout
import im.tox.toktok.TypedResource._
import im.tox.toktok.{ R, TR }
import org.scaloid.common._

final class CallSliderDecline(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int
) extends RelativeLayout(context, attrs, defStyle) with View.OnTouchListener {

  LayoutInflater.from(getContext).inflate(TR.layout.call_slider_decline, this, true)

  private val mCallImage = this.findView(TR.call_slider_img)
  private var mX = 0
  private var buttonWidth = 0
  private var barWidth = 0
  private var answered = false
  private val mCallText = this.findView(TR.call_slider_text)
  private var startPosition = 0
  private var listener: CallListener = null

  def this(context: Context, attrs: AttributeSet) { this(context, attrs, 0) }
  def this(context: Context) { this(context, null) }

  if (mCallImage != null) {
    mCallImage.setOnTouchListener(this)
  }

  def onTouch(v: View, motion: MotionEvent): Boolean = {
    val x = motion.getRawX.toInt
    val buttonPayoutParams = mCallImage.getLayoutParams.asInstanceOf[RelativeLayout.LayoutParams]
    motion.getAction match {
      case MotionEvent.ACTION_DOWN =>
        mX = x
        buttonWidth = mCallImage.getWidth
        barWidth = getWidth - buttonWidth - getPaddingRight - getPaddingLeft
        mCallImage.setImageResource(R.drawable.call_decline_hold)
        startPosition = mCallImage.x.toInt
        listener.onStart()
      case MotionEvent.ACTION_MOVE =>
        if ((mX - x) >= 0 && (mX - x) <= barWidth) {
          buttonPayoutParams.rightMargin = mX - x
          mCallText.setAlpha((mX - x).toFloat / (barWidth * 1.3f))
          answered = false
        } else if ((mX - x) >= barWidth) {
          buttonPayoutParams.rightMargin = barWidth
          answered = true
        } else if ((mX - x) <= 0) {
          buttonPayoutParams.rightMargin = 0
          mCallText.setAlpha(0)
        }
      case MotionEvent.ACTION_UP =>
        if (answered) {
          listener.onCompleted()
        } else {
          listener.onReleased()
          mCallImage.setImageResource(R.drawable.call_decline_button)
          buttonPayoutParams.rightMargin = 0
          mCallText.setAlpha(0)
        }
    }
    mCallImage.setLayoutParams(buttonPayoutParams)
    true
  }

  def setOnCallListener(listener: CallListener): Unit = {
    this.listener = listener
  }

}
