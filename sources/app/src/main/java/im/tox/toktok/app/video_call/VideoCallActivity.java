package im.tox.toktok.app.video_call;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.CompatUtil;

public final class VideoCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        Bundle bundle = getIntent().getExtras();

        int contactColor = BundleKey.contactPhotoReference.get(bundle);

        this.<ImageView>findViewById(R.id.call_background).setImageResource(contactColor);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(CompatUtil.getColor(getResources(), R.color.contactsTransparentBar));

        this.findViewById(R.id.call_ongoing_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final RelativeLayout bottom = this.findViewById(R.id.video_call_bar);

        Animation startAnimation = AnimationUtils.loadAnimation(bottom.getContext(), R.anim.abc_slide_in_bottom);

        startAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        startAnimation.setDuration(500);
        startAnimation.setStartOffset(500);

        bottom.startAnimation(startAnimation);
    }

}
