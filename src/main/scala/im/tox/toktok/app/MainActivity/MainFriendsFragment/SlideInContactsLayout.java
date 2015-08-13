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
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.tox.toktok.R;
import im.tox.toktok.app.CallActivity.CallActivity;
import im.tox.toktok.app.ContactsActivity.FileSendActivity;
import im.tox.toktok.app.Friend;
import im.tox.toktok.app.Message;
import im.tox.toktok.app.MessageActivity.MessageActivity;
import im.tox.toktok.app.SimpleDialogs.SimpleColorDialogDesign;
import im.tox.toktok.app.SimpleDialogs.SimpleDialogDesign;
import im.tox.toktok.app.SimpleDialogs.SimpleTextDialogDesign;
import im.tox.toktok.app.VideoCallActivity.VideoCallActivity;


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
    private View mStatusBar;

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
    private CardView mMessage;
    private CardView mSaveProfile;
    private CardView mFilesSend;
    private RelativeLayout mDeleteFriend;
    private RelativeLayout mBlockFriend;
    private RelativeLayout mChangeColor;

    private float scrollTop = 0;

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
        mStatusBar = findViewById(R.id.contacts_status_bar_color);
        mStatusBar.getLayoutParams().height = getStatusBarHeight();
        mMessage = (CardView) findViewById(R.id.contacts_message);
        mSaveProfile = (CardView) findViewById(R.id.contacts_save_photo);
        mFilesSend = (CardView) findViewById(R.id.contacts_file_download);
        mDeleteFriend = (RelativeLayout) findViewById(R.id.contacts_delete);
        mBlockFriend = (RelativeLayout) findViewById(R.id.contacts_block_friend);
        mChangeColor = (RelativeLayout) findViewById(R.id.contacts_edit_color);

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
            return false;
        }

        int action = MotionEventCompat.getActionMasked(ev);
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("Asda", "Intercept Touch DOWN");
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                Log.d("Asda", "Intercept Touch MOVE");
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                Log.d("Asda", "Intercept Touch UP");
                break;
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

        int action = ev.getAction();
        float y = ev.getY();
        View v = findViewById(R.id.contacts_nested);


        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_MOVE: {

                float dy = mInitialMotionY - y;

                break;
            }
            case MotionEvent.ACTION_UP: {
                float dy = mInitialMotionY - y;
                if (dy > 0) {
                    if (mDragOffset < 0.5 && !scrollActive) {
                        smoothSlideTo(0);
                        scrollActive = true;
                        mStatusBar.setVisibility(View.VISIBLE);
                        mStatusBar.bringToFront();
                        scrollTop = v.getBottom();
                    }
                } else {

                    if (!scrollActive && Math.abs(dy) > 20 ) {

                        if (mDragOffset > 0.5f) {
                            finish();
                        } else {
                            smoothSlideTo(0.5f);
                            mStatusBar.setVisibility(View.INVISIBLE);
                        }

                    }
                    else{
                        if(v.getBottom() >= (int)scrollTop){
                            scrollActive = false;
                        }
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


        mVideoCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();

                bundle.putInt("contactPhotoReference", friend.getPhotoReference());


                Intent newIntent = new Intent(activity, VideoCallActivity.class);
                newIntent.putExtras(bundle);
                activity.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                activity.startActivity(newIntent);
            }
        });

        mMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle = new Bundle();

                bundle.putString("messageTitle", friend.getUserName());
                bundle.putInt("contactColorPrimary", friend.getColor());
                bundle.putInt("contactColorStatus",friend.getSecondColor());
                bundle.putInt("imgResource", friend.getPhotoReference());
                bundle.putInt("messageType", 0);


                Intent newIntent = new Intent(activity, MessageActivity.class);
                newIntent.putExtras(bundle);
                activity.startActivity(newIntent);

            }
        });

        mSaveProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               Snackbar snack = Snackbar.make(mCoordinator, getResources().getString(R.string.contact_save_photo_snackbar), Snackbar.LENGTH_LONG);
                View snackView  =snack.getView();
                snackView.setBackgroundResource(R.color.snackBarColor);
                TextView snackText = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
                snackText.setTextColor(getResources().getColor(R.color.textDarkColor));
                snack.show();
            }
        });

        mFilesSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle = new Bundle();

                bundle.putString("contactName", friend.getUserName());
                bundle.putInt("contactColorPrimary", friend.getColor());
                bundle.putInt("contactColorStatus", friend.getSecondColor());

                Intent newIntent = new Intent(activity, FileSendActivity.class);
                newIntent.putExtras(bundle);
                activity.startActivity(newIntent);

            }
        });

        mDeleteFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDialogDesign dial = new SimpleDialogDesign(activity,getResources().getString(R.string.dialog_delete_friend)+" "+friend.getUserName()+" "+getResources().getString(R.string.dialog_delete_friend_end),friend.getColor(),R.drawable.ic_person_black_48dp,null);
                dial.show();
            }
        });

        mBlockFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snack = Snackbar.make(mCoordinator, getResources().getString(R.string.contact_blocked), Snackbar.LENGTH_LONG);
                View snackView  =snack.getView();
                snackView.setBackgroundResource(R.color.snackBarColor);
                TextView snackText = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
                snackText.setTextColor(getResources().getColor(R.color.textDarkColor));
                snack.show();
            }
        });

        mChangeColor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleColorDialogDesign dial = new SimpleColorDialogDesign(activity,getResources().getString(R.string.dialog_change_color)+" "+friend.getUserName()+" "+getResources().getString(R.string.dialog_change_color_end),friend.getColor(), R.drawable.ic_image_color_lens,0,null);
                dial.show();
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




