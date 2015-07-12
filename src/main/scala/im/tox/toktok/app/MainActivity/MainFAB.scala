package im.tox.toktok.app.MainActivity

import android.content.Context
import android.support.design.widget.{AppBarLayout, CoordinatorLayout, FloatingActionButton}
import android.util.AttributeSet
import android.view.View


class MainFAB(context : Context, attrs : AttributeSet) extends FloatingActionButton.Behavior{

  private val toolbarHeight : Int = 120


  override def layoutDependsOn( parent : CoordinatorLayout , fab : FloatingActionButton , dependency : View) : Boolean = {
    return super.layoutDependsOn(parent, fab, dependency) || (dependency.isInstanceOf[AppBarLayout]);

  }

  override def onDependentViewChanged( parent : CoordinatorLayout, fab : FloatingActionButton, dependency : View ) : Boolean = {
    val returnValue : Boolean = super.onDependentViewChanged(parent, fab, dependency);
    if (dependency.isInstanceOf[AppBarLayout]) {
      val lp : CoordinatorLayout.LayoutParams =  fab.getLayoutParams().asInstanceOf[CoordinatorLayout.LayoutParams];
      val fabBottomMargin : Int = lp.bottomMargin;
      val distanceToScroll : Int = fab.getHeight() + fabBottomMargin;
      val ratio : Float = dependency.getY()/toolbarHeight;
      fab.setTranslationY(-distanceToScroll * ratio);
    }
    return returnValue;
  }

}