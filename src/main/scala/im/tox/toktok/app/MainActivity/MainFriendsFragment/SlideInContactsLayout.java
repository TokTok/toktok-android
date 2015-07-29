package im.tox.toktok.app.MainActivity.MainFriendsFragment;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import im.tox.toktok.R;


public class SlideInContactsLayout extends ViewGroup {


    private final ViewDragHelper mDragHelper;

    private View mCoordinator;

    private float mInitialMotionY;

    private int mDragRange;
    private int mTop;
    private boolean scrollActive = false;
    private float mDragOffset;
    private TransitionDrawable backgroundTransition;

    public SlideInContactsLayout(Context context) {
        this(context, null);
    }

    public SlideInContactsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideInContactsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());

    }

    protected void onFinishInflate() {

        mCoordinator = findViewById(R.id.contacts_coordinator_layout);

        super.onFinishInflate();
    }

    public void start() {

        setVisibility(View.VISIBLE);

        backgroundTransition = (TransitionDrawable) getBackground();
        backgroundTransition.startTransition(500);


        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCoordinator.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mCoordinator.startAnimation(a);
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);

        if (mDragHelper.smoothSlideViewTo(mCoordinator, mCoordinator.getLeft(), y)) {
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


        if (scrollActive) {
            mDragHelper.cancel();
            return false;
        }

        Log.d("asdasd", mDragOffset + "");


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

    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);

        final float y = ev.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            Log.d("asdasd", "DOWN - " + y);

            mInitialMotionY = y;

        } else if (action == MotionEvent.ACTION_MOVE) {
            Log.d("asdasd", "MOVE - " + y);


            if(mTop == 0 && (y - mInitialMotionY) > 0){
                scrollActive = true;
            }

            else{
                scrollActive = false;
            }


        } else if (action == MotionEvent.ACTION_UP) {
            Log.d("asdasd", "UP - " + y);

            if (!scrollActive) {

                if (mDragOffset > 0.5f) {
                    smoothSlideTo(1f);
                    finish();
                } else {

                    if (y - mInitialMotionY > 0) {
                        smoothSlideTo(0.5f);
                    } else {
                        smoothSlideTo(0f);
                    }
                }

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
        mDragRange = getHeight();

        if (changed) {
            mTop = getHeight() / 2;
            mCoordinator.layout(0, getHeight() / 2, r, mTop + mCoordinator.getMeasuredHeight());
        } else {
            mCoordinator.layout(0, mTop, r, mTop + mCoordinator.getMeasuredHeight());
        }

    }

    private void finish() {

        mCoordinator.setVisibility(View.INVISIBLE);
        backgroundTransition.reverseTransition(500);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                setVisibility(View.GONE);
                smoothSlideTo(0.5f);

            }
        }, 500);

    }


    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mCoordinator;
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
            final int topBound = 0;
            final int bottomBound = getHeight();
            return Math.min(Math.max(top, topBound), bottomBound);
        }

    }

}




