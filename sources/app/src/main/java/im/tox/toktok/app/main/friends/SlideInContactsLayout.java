package im.tox.toktok.app.main.friends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.customview.widget.ViewDragHelper;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.CompatUtil;
import im.tox.toktok.app.call.CallActivity;
import im.tox.toktok.app.contacts.FileSendActivity;
import im.tox.toktok.app.domain.Friend;
import im.tox.toktok.app.message_activity.MessageActivity;
import im.tox.toktok.app.simple_dialogs.SimpleColorDialogDesign;
import im.tox.toktok.app.simple_dialogs.SimpleDialogDesign;
import im.tox.toktok.app.simple_dialogs.SimpleTextDialogDesign;
import im.tox.toktok.app.video_call.VideoCallActivity;

import static im.tox.toktok.app.TypedBundleKey.SBundle;

public final class SlideInContactsLayout extends ViewGroup {

    private static final Logger logger = LoggerFactory.getLogger(SlideInContactsLayout.class);

    @Nullable
    private Activity activity = null;

    @NonNull
    private final ViewDragHelper mDragHelper;
    private View mCoordinator;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mUserImage;
    private TextView mSubtitle;
    private TextView mTitle;
    private TextView mSettingsTitle;
    private Toolbar mToolbar;
    private View mStatusBar;
    private RelativeLayout mEditNameButton;
    private double mInitialMotionY;
    private int mDragRange;
    private int mTop;
    private Boolean scrollActive;
    private double mDragOffset;
    private TransitionDrawable backgroundTransition;
    private TextView mVoiceCall;
    private TextView mVideoCall;
    private CardView mMessage;
    private CardView mSaveProfile;
    private CardView mFilesSend;
    private RelativeLayout mDeleteFriend;
    private RelativeLayout mBlockFriend;
    private RelativeLayout mChangeColor;
    private int scrollTop;

    private static final int[] icons = {
            R.id.contacts_icon_call,
            R.id.contacts_icon_message,
            R.id.contacts_icon_image,
            R.id.contacts_icon_download,
            R.id.contacts_icon_palette,
            R.id.contacts_icon_edit,
            R.id.contacts_icon_trash,
            R.id.contacts_icon_lock
    };

    public SlideInContactsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    public SlideInContactsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideInContactsLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onFinishInflate() {
        mCoordinator = this.findViewById(R.id.contacts_coordinator_layout);
        mCollapsingToolbarLayout = this.findViewById(R.id.contacts_collapsing_toolbar);
        mFloatingActionButton = this.findViewById(R.id.contacts_FAB);
        mUserImage = this.findViewById(R.id.contact_image);
        mTitle = this.findViewById(R.id.contact_title);
        mSubtitle = this.findViewById(R.id.contact_subtitle);
        mSettingsTitle = this.findViewById(R.id.contacts_other_title);
        mToolbar = this.findViewById(R.id.contacts_toolbar);
        mVoiceCall = this.findViewById(R.id.contacts_item_voice_call);
        mVideoCall = this.findViewById(R.id.contacts_item_video_call);
        mEditNameButton = this.findViewById(R.id.contacts_edit_alias);
        mStatusBar = this.findViewById(R.id.contacts_status_bar_color);
        mStatusBar.getLayoutParams().height = getStatusBarHeight();
        mMessage = this.findViewById(R.id.contacts_message);
        mSaveProfile = this.findViewById(R.id.contacts_save_photo);
        mFilesSend = this.findViewById(R.id.contacts_file_download);
        mDeleteFriend = this.findViewById(R.id.contacts_delete);
        mBlockFriend = this.findViewById(R.id.contacts_block_friend);
        mChangeColor = this.findViewById(R.id.contacts_edit_color);
        super.onFinishInflate();
    }

    public void start(Activity activity, @NonNull Friend friend, @Px int actionBarHeight) {
        this.activity = activity;
        mTitle.setText(friend.userName);

        mCollapsingToolbarLayout.setBackgroundColor(friend.color);
        mCollapsingToolbarLayout.setContentScrimColor(friend.color);
        mCollapsingToolbarLayout.setStatusBarScrimColor(friend.secondColor);

        mUserImage.setImageResource(friend.photoReference);

        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(friend.color));

        mSubtitle.setText(friend.userMessage);

        mSettingsTitle.setTextColor(ColorStateList.valueOf(friend.color));

        CollapsingToolbarLayout.LayoutParams b = (CollapsingToolbarLayout.LayoutParams) mToolbar.getLayoutParams();
        b.height = actionBarHeight + getStatusBarHeight();
        mToolbar.setLayoutParams(b);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        for (int item : icons) {
            ImageView icon = this.findViewById(item);
            icon.setImageTintList(ColorStateList.valueOf(friend.color));
        }

        initListeners(friend);
        setVisibility(View.VISIBLE);

        backgroundTransition = (TransitionDrawable) getBackground();
        backgroundTransition.startTransition(500);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
        animation.setAnimationListener(new Animation.AnimationListener() {
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
        mCoordinator.startAnimation(animation);
    }

    private void smoothSlideTo(Float slideOffset) {
        int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);
        if (mDragHelper.smoothSlideViewTo(mCoordinator, mCoordinator.getLeft(), y)) {
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
        if (scrollActive) {
            mDragHelper.cancel();
            return false;
        }

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                logger.debug("Intercept Touch DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                logger.debug("Intercept Touch MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                logger.debug("Intercept Touch UP");
                break;
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

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        Float y = ev.getY();
        NestedScrollView v = this.findViewById(R.id.contacts_nested);

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mInitialMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                double dy = mInitialMotionY - y;
                if (dy > 0) {
                    if (mDragOffset < 0.5 && !scrollActive) {
                        smoothSlideTo(0.f);
                        scrollActive = true;
                        mStatusBar.setVisibility(View.VISIBLE);
                        mStatusBar.bringToFront();
                        scrollTop = v.getBottom();
                    }
                } else {
                    if (!scrollActive && Math.abs(dy) > 20) {
                        if (mDragOffset > 0.5f) {
                            finish();
                        } else {
                            smoothSlideTo(0.5f);
                            mStatusBar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        if (v.getBottom() >= scrollTop) {
                            scrollActive = false;
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
        setMeasuredDimension(
                View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        );
    }

    @Override
    protected void onLayout(boolean changed, @Px int left, @Px int top, @Px int right, @Px int bottom) {
        mDragRange = getHeight();
        if (changed) {
            mTop = getHeight() / 2;
            mCoordinator.layout(0, getHeight() / 2, right, mTop + mCoordinator.getMeasuredHeight());
        } else {
            mCoordinator.layout(0, mTop, right, mTop + mCoordinator.getMeasuredHeight());
        }
    }

    private final Runnable DoNothing = () -> {
    };

    private void finish() {
        finish(DoNothing);
    }

    public void finish(@NonNull final Runnable after) {
        smoothSlideTo(1f);
        backgroundTransition.reverseTransition(500);
        new Handler().postDelayed(() -> {
            mCoordinator.setVisibility(View.INVISIBLE);
            setVisibility(View.GONE);
            after.run();
        }, 500);
    }

    private final class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mCoordinator;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, @Px int dx, @Px int dy) {
            mTop = top;
            mDragOffset = (float) top / mDragRange;
            requestLayout();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int topBound = 0;
            int bottomBound = getHeight();
            return Math.min(Math.max(top, topBound), bottomBound);
        }
    }

    private void initListeners(@NonNull final Friend friend) {
        mEditNameButton.setOnClickListener(v -> {
            SimpleTextDialogDesign dial = new SimpleTextDialogDesign(
                    activity,
                    getResources().getString(R.string.contact_popup_edit_alias),
                    friend.color,
                    R.drawable.ic_person_black_48dp,
                    friend.userName,
                    null
            );
            dial.show();
        });

        mVoiceCall.setOnClickListener(v -> activity.startActivity(new Intent(activity, CallActivity.class).putExtras(SBundle(
                BundleKey.contactName.map(friend.userName),
                BundleKey.contactColorPrimary.map(friend.color),
                BundleKey.contactPhotoReference.map(friend.photoReference)
        ))));

        mVideoCall.setOnClickListener(v -> {
            activity.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
            activity.startActivity(new Intent(activity, VideoCallActivity.class).putExtras(SBundle(
                    BundleKey.contactPhotoReference.map(friend.photoReference)
            )));
        });

        mMessage.setOnClickListener(v -> activity.startActivity(new Intent(activity, MessageActivity.class).putExtras(SBundle(
                BundleKey.messageTitle.map(friend.userName),
                BundleKey.contactColorPrimary.map(friend.color),
                BundleKey.contactColorStatus.map(friend.secondColor),
                BundleKey.imgResource.map(friend.photoReference),
                BundleKey.messageType.map(0)
        ))));

        mSaveProfile.setOnClickListener(v -> {
            Snackbar snack = Snackbar.make(
                    mCoordinator,
                    getResources().getString(R.string.contact_save_photo_snack_bar),
                    Snackbar.LENGTH_LONG
            );
            View snackView = snack.getView();
            snackView.setBackgroundResource(R.color.snackBarColor);
            TextView snackText = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
            snackText.setTextColor(CompatUtil.getColor(getResources(), R.color.textDarkColor));
            snack.show();
        });

        mFilesSend.setOnClickListener(v -> activity.startActivity(new Intent(activity, FileSendActivity.class).putExtras(SBundle(
                BundleKey.contactName.map(friend.userName),
                BundleKey.contactColorPrimary.map(friend.color),
                BundleKey.contactColorStatus.map(friend.secondColor)
        ))));

        mDeleteFriend.setOnClickListener(v -> {
            SimpleDialogDesign dial = new SimpleDialogDesign(
                    activity,
                    getResources().getString(R.string.dialog_delete_friend) + " " +
                            friend.userName + " " +
                            getResources().getString(R.string.dialog_delete_friend_end),
                    friend.color, R.drawable.ic_person_black_48dp, null
            );
            dial.show();
        });

        mBlockFriend.setOnClickListener(v -> {
            Snackbar snack = Snackbar.make(
                    mCoordinator,
                    getResources().getString(R.string.contact_blocked),
                    Snackbar.LENGTH_LONG
            );
            View snackView = snack.getView();
            snackView.setBackgroundResource(R.color.snackBarColor);
            TextView snackText = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
            snackText.setTextColor(CompatUtil.getColor(getResources(), R.color.textDarkColor));
            snack.show();
        });

        mChangeColor.setOnClickListener(v -> {
            SimpleColorDialogDesign dial = new SimpleColorDialogDesign(
                    activity,
                    getResources().getString(R.string.dialog_change_color) + " " +
                            friend.userName + " " +
                            getResources().getString(R.string.dialog_change_color_end),
                    friend.color, R.drawable.ic_image_color_lens, 0, null
            );
            dial.show();
        });
    }

    private @Px
    int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
