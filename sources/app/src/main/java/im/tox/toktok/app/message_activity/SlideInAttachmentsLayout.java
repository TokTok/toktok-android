package im.tox.toktok.app.message_activity;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.tox.toktok.R;

final class SlideInAttachmentsLayout extends ViewGroup {

    private static final Logger logger = LoggerFactory.getLogger(SlideInAttachmentsLayout.class);

    @NonNull
    private final ViewDragHelper mDragHelper;
    private View mChild;
    private float mInitialMotionY;
    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public SlideInAttachmentsLayout(
            Context context,
            AttributeSet attrs,
            int defStyle
    ) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    public SlideInAttachmentsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideInAttachmentsLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onFinishInflate() {
        mChild = this.findViewById(R.id.fragment_attachments);
        super.onFinishInflate();
    }

    private void smoothSlideTo(float slideOffset) {
        int topBound = getHeight() - mChild.getHeight();
        int y = (int) (topBound + slideOffset * mDragRange);
        if (mDragHelper.smoothSlideViewTo(mChild, mChild.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        float y = ev.getY();

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                logger.debug("DOWN");
                mInitialMotionY = y;
                break;

            case MotionEvent.ACTION_UP:
                logger.debug("UP");
                return true;
        }

        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        try {
            mDragHelper.processTouchEvent(ev);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    void start() {
        setVisibility(View.VISIBLE);
        smoothSlideTo(0f);
    }

    void finish() {
        smoothSlideTo(1f);
        new Handler().postDelayed(() -> setVisibility(View.INVISIBLE), 500);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        float y = ev.getY();

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mInitialMotionY = y;
                if (y < getHeight() - mChild.getHeight()) {
                    finish();
                }
                break;

            case MotionEvent.ACTION_UP:
                float dy = y - mInitialMotionY;
                if ((dy > 0 && (dy / mChild.getHeight()) > 0.3) || dy == 0) {
                    finish();
                } else {
                    smoothSlideTo(0f);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0), View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, @Px int r, int b) {
        mDragRange = mChild.getHeight();
        if (changed) {
            mTop = getHeight() - mChild.getHeight();
            mChild.layout(0, getHeight() - mChild.getHeight(), r, getHeight());
        } else {
            mChild.layout(0, mTop, r, mTop + mChild.getMeasuredHeight());
        }
    }

    private final class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mChild;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, @Px int dx, @Px int dy) {
            mTop = top;
            mDragOffset = (float) top / mDragRange;
            requestLayout();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int topBound = getHeight() - mChild.getHeight();
            int bottomBound = getHeight() + mChild.getPaddingTop();
            return Math.min(Math.max(top, topBound), bottomBound);
        }

    }

}
