package im.tox.toktok.app.call;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import im.tox.toktok.R;

public final class CallSliderDecline extends RelativeLayout implements View.OnTouchListener {

    private final ImageView mCallImage;
    private int mX;
    private int barWidth;
    private Boolean answered;
    private final TextView mCallText;
    private CallListener listener;

    public CallSliderDecline(
            Context context,
            AttributeSet attrs,
            int defStyle
    ) {
        super(context, attrs, defStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.call_slider_decline, this, true);
        mCallImage = findViewById(R.id.call_slider_img);
        if (mCallImage != null) {
            mCallImage.setOnTouchListener(this);
        }
        mCallText = findViewById(R.id.call_slider_text);
    }

    public CallSliderDecline(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CallSliderDecline(Context context) {
        this(context, null);
    }

    @Override
    public boolean onTouch(View v, @NonNull MotionEvent motion) {
        int x = (int) motion.getRawX();
        RelativeLayout.LayoutParams buttonPayoutParams = (LayoutParams) mCallImage.getLayoutParams();

        switch (motion.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = x;
                int buttonWidth = mCallImage.getWidth();
                barWidth = getWidth() - buttonWidth - getPaddingRight() - getPaddingLeft();
                mCallImage.setImageResource(R.drawable.call_decline_hold);
                listener.onStart();
                break;

            case MotionEvent.ACTION_MOVE:
                if ((mX - x) >= 0 && (mX - x) <= barWidth) {
                    buttonPayoutParams.rightMargin = mX - x;
                    mCallText.setAlpha((float) (mX - x) / (barWidth * 1.3f));
                    answered = false;
                } else if ((mX - x) >= barWidth) {
                    buttonPayoutParams.rightMargin = barWidth;
                    answered = true;
                } else if ((mX - x) <= 0) {
                    buttonPayoutParams.rightMargin = 0;
                    mCallText.setAlpha(0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (answered) {
                    listener.onCompleted();
                } else {
                    listener.onReleased();
                    mCallImage.setImageResource(R.drawable.call_decline_button);
                    buttonPayoutParams.rightMargin = 0;
                    mCallText.setAlpha(0);
                }
                break;
        }

        mCallImage.setLayoutParams(buttonPayoutParams);
        return true;
    }

    void setOnCallListener(CallListener listener) {
        this.listener = listener;
    }

}
