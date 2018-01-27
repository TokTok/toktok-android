package im.tox.toktok.app.main

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View.MeasureSpec
import android.view.animation.{Animation, AnimationUtils}
import android.view.{View, ViewGroup}
import android.widget.{EditText, LinearLayout}
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.main.HomeSearch.AfterFinish
import im.tox.toktok.{R, TR}

object HomeSearch {
  trait AfterFinish {
    def run(): Unit
  }
}

final class HomeSearch(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int
) extends ViewGroup(context, attrs, defStyle) {

  private var mBackground: TransitionDrawable = null
  private var mBase: LinearLayout = null
  private var mInput: EditText = null
  private var mCardView: CardView = null
  private var mRecycler: NestedScrollView = null

  def this(context: Context, attrs: AttributeSet) { this(context, attrs, 0) }
  def this(context: Context) { this(context, null) }

  protected override def onFinishInflate(): Unit = {
    mBackground = getBackground.asInstanceOf[TransitionDrawable]
    mBackground.startTransition(500)

    mBase = this.findView(TR.home_search_layout)
    mCardView = this.findView(TR.home_search_bar)
    mRecycler = this.findView(TR.home_search_bar_recycler)

    val searchBarAnimation = AnimationUtils.loadAnimation(mCardView.getContext, R.anim.abc_fade_in)
    mCardView.startAnimation(searchBarAnimation)

    mInput = this.findView(TR.home_search_input)

    super.onFinishInflate()
  }

  protected override def onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int): Unit = {
    mBase.layout(0, getStatusBarHeight, getWidth, getHeight)
  }

  private def getStatusBarHeight: Int = {
    val resourceId = getResources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
      getResources.getDimensionPixelSize(resourceId)
    } else {
      0
    }
  }

  protected override def onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int): Unit = {
    measureChildren(widthMeasureSpec, heightMeasureSpec)
    val maxWidth: Int = MeasureSpec.getSize(widthMeasureSpec)
    val maxHeight: Int = MeasureSpec.getSize(heightMeasureSpec)
    setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0), View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0))
  }

  def finish(after: AfterFinish): Unit = {
    if (mInput.isFocusable) {
      mInput.clearFocus()
    }

    val searchBarAnimation = AnimationUtils.loadAnimation(mCardView.getContext, R.anim.abc_fade_out)

    searchBarAnimation.setAnimationListener(new Animation.AnimationListener() {
      def onAnimationStart(animation: Animation): Unit = {}

      def onAnimationEnd(animation: Animation): Unit = {
        mCardView.setVisibility(View.INVISIBLE)
        setVisibility(View.GONE)
        mBackground.reverseTransition(500)
        after.run()
      }

      def onAnimationRepeat(animation: Animation): Unit = {}
    })
    mCardView.startAnimation(searchBarAnimation)
  }

}
