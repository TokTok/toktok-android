package im.tox.toktok.app.call;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.tox.toktok.R;

public final class CallSliderAnswer extends RelativeLayout implements View.OnTouchListener {

    private final ImageView mCallImage;
    private int mX;
    private int barWidth;
    private Boolean answered;
    private final TextView mCallText;
    private CallListener listener;

    public CallSliderAnswer(
            Context context,
            AttributeSet attrs,
            int defStyle
    ) {
        super(context, attrs, defStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.call_slider_answer, this, true);
        mCallImage = findViewById(R.id.call_slider_img);
        if (mCallImage != null) {
            mCallImage.setOnTouchListener(this);
        }
        mCallText = findViewById(R.id.call_slider_text);
    }

    public CallSliderAnswer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CallSliderAnswer(Context context) {
        this(context, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motion) {
        int x = (int) motion.getRawX();
        RelativeLayout.LayoutParams buttonPayoutParams = (LayoutParams) mCallImage.getLayoutParams();

        switch (motion.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = x;
                int buttonWidth = mCallImage.getWidth();
                barWidth = getWidth() - buttonWidth - getPaddingRight() - getPaddingLeft();
                mCallImage.setImageResource(R.drawable.call_answer_hold);
                listener.onStart();
                break;

            case MotionEvent.ACTION_MOVE:
                if ((x - mX) >= 0 && (x - mX) <= barWidth) {
                    buttonPayoutParams.leftMargin = x - mX;
                    mCallText.setAlpha((float) (x - mX) / (barWidth * 1.3f));
                    answered = false;
                } else if ((x - mX) < 0) {
                    buttonPayoutParams.leftMargin = 0;
                    mCallText.setAlpha(0);
                } else if ((x - mX) >= barWidth) {
                    buttonPayoutParams.leftMargin = barWidth;
                    answered = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (answered) {
                    listener.onCompleted();
                } else {
                    listener.onReleased();
                    mCallImage.setImageResource(R.drawable.call_answer_button);
                    buttonPayoutParams.leftMargin = 0;
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
