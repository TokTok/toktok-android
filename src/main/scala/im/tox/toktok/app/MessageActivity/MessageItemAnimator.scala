package im.tox.toktok.app.MessageActivity

import android.support.v4.view.ViewCompat
import android.support.v7.widget.{RecyclerView, DefaultItemAnimator}
import android.util.Log
import android.view.animation.Animation.AnimationListener
import android.view.animation.{Animation, AnimationUtils}
import im.tox.toktok.R


class MessageItemAnimator extends DefaultItemAnimator(){

  override def animateAdd(holder : RecyclerView.ViewHolder) : Boolean = {

      val a = AnimationUtils.loadAnimation(holder.itemView.getContext,R.anim.slide_in_bottom)
      a.setDuration(500)
      holder.itemView.startAnimation(a)
      dispatchAddFinished(holder)
      return true


  }

  override def animateRemove(holder : RecyclerView.ViewHolder): Boolean ={

    val a = AnimationUtils.loadAnimation(holder.itemView.getContext,R.anim.abc_fade_out)
    holder.itemView.startAnimation(a)
    dispatchRemoveFinished(holder)
    return true
  }

}