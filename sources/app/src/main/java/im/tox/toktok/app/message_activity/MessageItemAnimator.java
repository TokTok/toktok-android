package im.tox.toktok.app.message_activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import im.tox.toktok.R;

final class MessageItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        Animation a = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_in_bottom);
        a.setDuration(500);
        holder.itemView.startAnimation(a);
        dispatchAddFinished(holder);
        return true;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        Animation a = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.abc_fade_out);
        holder.itemView.startAnimation(a);
        dispatchRemoveFinished(holder);
        return true;
    }

}
