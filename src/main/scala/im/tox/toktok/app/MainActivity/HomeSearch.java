package im.tox.toktok.app.MainActivity;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import im.tox.toktok.R;

public class HomeSearch extends ViewGroup {

    private TransitionDrawable background = null;
    private LinearLayout mBase = null;
    private EditText mInput = null;
    private CardView mCardView = null;
    private NestedScrollView mRecycler = null;

    public HomeSearch(Context context) {
        this(context, null);
    }

    public HomeSearch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeSearch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    protected void onFinishInflate() {

        background = (TransitionDrawable) getBackground();
        background.startTransition(500);
        mBase = (LinearLayout) findViewById(R.id.home_search_layout);

        mCardView = (CardView) findViewById(R.id.home_search_bar);
        mRecycler = (NestedScrollView) findViewById(R.id.home_search_bar_recycler);

        int cx = mCardView.getMeasuredWidth();
        int cy = mCardView.getMeasuredHeight() / 2;

        Animation searchBarAnimation = AnimationUtils.loadAnimation(mCardView.getContext(),R.anim.abc_fade_in);
        mCardView.startAnimation(searchBarAnimation);

        mInput = (EditText) findViewById(R.id.home_search_input);

        super.onFinishInflate();

    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mBase.layout(0, getStatusBarHeight(), getWidth(), getHeight());

    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    public void finish(){
        if(mInput.isFocusable()){
            mInput.clearFocus();
        }

        Animation searchBarAnimation = AnimationUtils.loadAnimation(mCardView.getContext(),R.anim.abc_fade_out);

        searchBarAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardView.setVisibility(View.INVISIBLE);
                background.reverseTransition(500);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mCardView.startAnimation(searchBarAnimation);

    }
}
