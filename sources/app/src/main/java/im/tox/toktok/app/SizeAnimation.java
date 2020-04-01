package im.tox.toktok.app;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;

public final class SizeAnimation extends Animation {

    @NonNull
    private final View view;
    private final int oldSize;
    private final int newSize;
    private final int sizeChange;

    public SizeAnimation(@NonNull View view, int newSize, int sizeChange) {
        this.view = view;
        this.oldSize = sizeChange == 0 ? view.getWidth() : view.getHeight();
        this.newSize = newSize;
        this.sizeChange = sizeChange;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final int currentSize = (int) (oldSize + ((newSize - oldSize) * interpolatedTime));

        switch (sizeChange) {
            case 0:
                view.getLayoutParams().width = currentSize;
                break;
            case 1:
                view.getLayoutParams().height = currentSize;
                break;
        }

        view.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

}
