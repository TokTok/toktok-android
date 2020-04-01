package im.tox.toktok.app.message_activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar.SnackbarLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public final class MessageInputBarBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {

    public MessageInputBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return dependency instanceof SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, @NonNull RelativeLayout child, @NonNull View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

}
