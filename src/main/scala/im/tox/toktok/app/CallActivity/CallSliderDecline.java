package im.tox.toktok.app.CallActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.tox.toktok.R;


public class CallSliderDecline extends RelativeLayout implements View.OnTouchListener {

    private Context mContext;
    private ImageView mCallImage;
    private int _X = 0;
    private int buttonWidth = 0;
    private int barWidth = 0;
    private Boolean answered = false;
    private TextView mCallText;
    private int startPosition = 0;
    private CallListener listener = null;


    public CallSliderDecline(Context context) {
        super(context);
        mContext = context;
        createView();
    }

    public CallSliderDecline(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        createView();
    }

    public CallSliderDecline(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        createView();
    }

    public void createView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.call_slider_decline, this, true);
        mCallImage = (ImageView) findViewById(R.id.call_slider_img);
        mCallImage.setOnTouchListener(this);
        mCallText = (TextView) findViewById(R.id.call_slider_text);


    }

    public boolean onTouch(View v, MotionEvent motion) {
        int x = (int) motion.getRawX();
        int y = (int) motion.getRawY();

        RelativeLayout.LayoutParams buttonPayoutParams = (RelativeLayout.LayoutParams) mCallImage.getLayoutParams();


        switch (motion.getAction()) {

            case MotionEvent.ACTION_DOWN:

                _X = x;
                buttonWidth = mCallImage.getWidth();
                barWidth = getWidth() - buttonWidth - getPaddingRight() - getPaddingLeft();
                mCallImage.setImageResource(R.drawable.call_decline_hold);
                startPosition = (int)mCallImage.getX();
                listener.onStart();
                break;
            case MotionEvent.ACTION_MOVE:

                if ((_X - x) >= 0 && (_X - x) <= barWidth) {
                    buttonPayoutParams.rightMargin = _X - x ;
                    mCallText.setAlpha((float)(_X - x)/(barWidth*1.3f));
                    answered = false;
                }
                else if ((_X - x) >= barWidth) {
                    buttonPayoutParams.rightMargin = barWidth;
                    answered = true;

                }
                else if ((_X - x) <= 0) {
                    buttonPayoutParams.rightMargin = 0;
                    mCallText.setAlpha(0);
                }
                break;

            case MotionEvent.ACTION_UP:

                if (answered) {
                    listener.onCompleted();
                }
                else {
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

    public void setOnCallListener(CallListener listener) {
        this.listener = listener;
    }



}
