package im.tox.toktok.app.main;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import im.tox.toktok.R;

public final class HomeSearch extends ViewGroup {

    @Nullable
    private TransitionDrawable mBackground = null;
    @Nullable
    private LinearLayout mBase = null;
    @Nullable
    private EditText mInput = null;
    @Nullable
    private CardView mCardView = null;

    public HomeSearch(
            Context context,
            AttributeSet attrs,
            int defStyle
    ) {
        super(context, attrs, defStyle);
    }

    public HomeSearch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeSearch(Context context) {
        this(context, null);
    }

    @Override
    protected void onFinishInflate() {
        mBackground = (TransitionDrawable) getBackground();
        mBackground.startTransition(500);

        mBase = this.findViewById(R.id.home_search_layout);
        mCardView = this.findViewById(R.id.home_search_bar);
        NestedScrollView mRecycler = this.findViewById(R.id.home_search_bar_recycler);

        Animation searchBarAnimation = AnimationUtils.loadAnimation(mCardView.getContext(), R.anim.abc_fade_in);
        mCardView.startAnimation(searchBarAnimation);

        mInput = this.findViewById(R.id.home_search_input);

        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mBase.layout(0, getStatusBarHeight(), getWidth(), getHeight());
    }

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                View.resolveSizeAndState(
                        maxWidth,
                        widthMeasureSpec,
                        0
                ),
                View.resolveSizeAndState(
                        maxHeight,
                        heightMeasureSpec,
                        0
                )
        );
    }

    public void finish(@NonNull final Runnable after) {
        if (mInput.isFocusable()) {
            mInput.clearFocus();
        }

        Animation searchBarAnimation = AnimationUtils.loadAnimation(mCardView.getContext(), R.anim.abc_fade_out);

        searchBarAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardView.setVisibility(View.INVISIBLE);
                setVisibility(View.GONE);
                mBackground.reverseTransition(500);
                after.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mCardView.startAnimation(searchBarAnimation);
    }

}
