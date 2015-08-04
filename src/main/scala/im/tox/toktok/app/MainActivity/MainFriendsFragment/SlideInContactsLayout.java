package im.tox.toktok.app.MainActivity.MainFriendsFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.tox.toktok.R;
import im.tox.toktok.app.CallActivity.CallActivity;
import im.tox.toktok.app.Friend;
import im.tox.toktok.app.SimpleDialogs.SimpleTextDialogDesign;


public class SlideInContactsLayout extends ViewGroup {


    private final ViewDragHelper mDragHelper;

    private View mCoordinator;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mUserImage;
    private TextView mSubtitle;
    private TextView mTitle;
    private TextView mSettingsTitle;
    private android.support.v7.widget.Toolbar mToolbar;

    private RelativeLayout mEditNameButton;

    private Activity activity;

    private Friend friend;

    private float mInitialMotionY;
    private int mDragRange;
    private int mTop;
    private boolean scrollActive = false;
    private float mDragOffset;
    private TransitionDrawable backgroundTransition;
    private TextView mVoiceCall;
    private TextView mVideoCall;

    private Bundle bundle;

    private final int[] icons = {R.id.contacts_icon_call, R.id.contacts_icon_message, R.id.contacts_icon_image, R.id.contacts_icon_download, R.id.contacts_icon_palette, R.id.contacts_icon_edit, R.id.contacts_icon_trash, R.id.contacts_icon_lock};


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
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.contacts_collapsing_toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.contacts_FAB);
        mUserImage = (ImageView) findViewById(R.id.contact_image);
        mTitle = (TextView) findViewById(R.id.contact_title);
        mSubtitle = (TextView) findViewById(R.id.contact_subtitle);
        mSettingsTitle = (TextView) findViewById(R.id.contacts_other_title);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.contacts_toolbar);
        mVoiceCall = (TextView) findViewById(R.id.contacts_item_voice_call);
        mVideoCall = (TextView) findViewById(R.id.contacts_item_video_call);
        mEditNameButton = (RelativeLayout) findViewById(R.id.contacts_edit_alias);

        super.onFinishInflate();
    }

    public void start(Activity activity, final Friend friend, int actionBarHeight) {

        this.activity = activity;

        mTitle.setText(friend.getUserName());
        mCollapsingToolbarLayout.setBackgroundColor(friend.getColor());
        mCollapsingToolbarLayout.setContentScrimColor(friend.getColor());
        mCollapsingToolbarLayout.setStatusBarScrimColor(friend.getSecondColor());
        mUserImage.setImageResource(friend.getPhotoReference());
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(friend.getColor()));
        mSubtitle.setText(friend.getUserMessage());
        mSettingsTitle.setTextColor(ColorStateList.valueOf(friend.getColor()));

        CollapsingToolbarLayout.LayoutParams b = (CollapsingToolbarLayout.LayoutParams) mToolbar.getLayoutParams();
        b.height = actionBarHeight + getStatusBarHeight();
        mToolbar.setLayoutParams(b);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);


        for (int i = 0; i < icons.length; i++) {
            ImageView icon = (ImageView) findViewById(icons[i]);
            icon.setImageTintList(ColorStateList.valueOf(friend.getColor()));
        }

        initListeners(friend);

        this.friend = friend;

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
            Log.d("asd", "Scrolling");
            return false;
        }

        Log.d("asdasd", scrollActive + "");


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
            Log.d("asdasd", "MOVE - " + y + " - " + mTop);


            if (mTop == 0 && (y - mInitialMotionY) > 0) {
                scrollActive = true;
                Log.d("asdasd", "scrollActivated");
            } else if (mTop == 0 && scrollActive) {

                if (mCoordinator.getScrollY() == 0 && (y - mInitialMotionY) < 0) {
                    scrollActive = false;
                    Log.d("asdasd", "scrollDeactivated");
                }

            }


        } else if (action == MotionEvent.ACTION_UP && !scrollActive) {

            Log.d("asdasd", "UP - " + y);

            if (mDragOffset > 0.5f) {
                finish();
            } else {

                if (y - mInitialMotionY > 0) {
                    smoothSlideTo(0.5f);
                } else {
                    smoothSlideTo(0f);
                }
            }

        } else if (action == MotionEvent.ACTION_UP && scrollActive) {

            mDragHelper.shouldInterceptTouchEvent(ev);

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

    public void finish() {

        smoothSlideTo(1f);
        backgroundTransition.reverseTransition(500);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mCoordinator.setVisibility(View.INVISIBLE);
                setVisibility(View.GONE);

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

    private void initListeners(final Friend friend) {


        mEditNameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleTextDialogDesign dial = new SimpleTextDialogDesign(activity, getResources().getString(R.string.contact_popup_edit_alias), friend.getColor(), R.drawable.ic_person_black_48dp, friend.getUserName(), null);
                dial.show();
            }
        });


        mVoiceCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle = new Bundle();

                bundle.putString("contactName", friend.getUserName());
                bundle.putInt("contactColorPrimary", friend.getColor());
                bundle.putInt("contactPhotoReference", friend.getPhotoReference());


                Intent newIntent = new Intent(activity, CallActivity.class);
                newIntent.putExtras(bundle);
                activity.startActivity(newIntent);

            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}




