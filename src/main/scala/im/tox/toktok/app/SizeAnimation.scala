package im.tox.toktok.app

import android.view.View
import android.view.animation.{ Animation, Transformation }

final class SizeAnimation(view: View, newSize: Int, sizeChange: Int) extends Animation {

  private val oldSize = {
    if (sizeChange == 0) {
      view.getWidth
    } else {
      view.getHeight
    }
  }

  protected override def applyTransformation(interpolatedTime: Float, t: Transformation): Unit = {
    val currentSize = oldSize + ((newSize - oldSize) * interpolatedTime).toInt

    sizeChange match {
      case 0 => view.getLayoutParams.width = currentSize
      case 1 => view.getLayoutParams.height = currentSize
    }

    view.requestLayout()
  }

  protected override def willChangeBounds: Boolean = true

}
