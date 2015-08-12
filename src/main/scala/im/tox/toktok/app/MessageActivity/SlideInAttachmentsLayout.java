package im.tox.toktok.app.MessageActivity;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import im.tox.toktok.R;


public class SlideInAttachmentsLayout extends ViewGroup {


    private final ViewDragHelper mDragHelper;
    private View mChild;

    private float mInitialMotionY;
    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public SlideInAttachmentsLayout(Context context) {
        this(context, null);
    }

    public SlideInAttachmentsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideInAttachmentsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());

    }

    protected void onFinishInflate() {

        mChild = findViewById(R.id.fragment_attachments);

        super.onFinishInflate();
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getHeight() - mChild.getHeight();
        int y = (int) (topBound + slideOffset * mDragRange);

        if (mDragHelper.smoothSlideViewTo(mChild, mChild.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);

        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("asdasd", "DOWN");
                mInitialMotionY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                Log.d("asdasd", "UP");

                return true;


            }
        }

        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        try {
            mDragHelper.processTouchEvent(ev);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public void start(){
        setVisibility(View.VISIBLE);
        smoothSlideTo(0f);
    }

    public void finish(){
        smoothSlideTo(1f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.INVISIBLE);

            }
        }, 500);

    }


    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);

        final float y = ev.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            mInitialMotionY = y;

            if(y < getHeight()-mChild.getHeight()){
                finish();
            }

        } else if (action == MotionEvent.ACTION_UP) {


            float dy = y - mInitialMotionY;

            if( (dy > 0 && (dy/mChild.getHeight()) > 0.3) || dy == 0){
                finish();
            }


            else{
                smoothSlideTo(0f);
            }
        }

        return super.dispatchTouchEvent(ev);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = mChild.getHeight();

        if (changed) {
            mTop = getHeight()-mChild.getHeight();
            mChild.layout(0, getHeight()-mChild.getHeight(), r, getHeight());
        } else {
            mChild.layout(0, mTop, r, mTop + mChild.getMeasuredHeight());
        }
    }


    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mChild;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            mDragOffset = (float) top / mDragRange;
            requestLayout();
        }


        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getHeight() - mChild.getHeight();
            final int bottomBound = getHeight()+mChild.getPaddingTop();
            return Math.min(Math.max(top, topBound), bottomBound);
        }

    }

}





