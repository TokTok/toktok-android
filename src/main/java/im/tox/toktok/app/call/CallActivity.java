package im.tox.toktok.app.call;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.BundleKey;
import im.tox.toktok.R;
import im.tox.toktok.app.domain.Friend;
import im.tox.toktok.app.video_call.VideoCallActivity;

public final class CallActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger(CallActivity.class);

    private int viewType = 2;
    private Boolean backgroundInitialised = false;

    private CallActivityViewHolder onCreateViewHolder() {
        return new CallActivityViewHolder(this);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_layout);

        onCreate(onCreateViewHolder());
    }

    private void onCreate(CallActivityViewHolder holder) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(getResources().getColor(R.color.contactsTransparentBar, null));

        holder.bottomPanel.setBackgroundColor(Color.argb(
                165,
                Color.red(holder.friendColor),
                Color.green(holder.friendColor),
                Color.blue(holder.friendColor)
        ));

        initBackground(holder, holder.friendImgSrc);

        switch (viewType) {
            case 0:
            case 2:
                initReceiveCall(holder);
                break;
            case 1:
                initOnGoingCall(holder);
                break;
        }
    }

    private void initOnGoingCall(CallActivityViewHolder holder) {
        holder.topPanel.setBackgroundColor(getResources().getColor(R.color.callTopColor, null));
        holder.topPanel.addView(getLayoutInflater().inflate(R.layout.call_top_on_going, null, false));
        holder.bottomPanel.addView(getLayoutInflater().inflate(R.layout.call_bottom_on_going, null, false));

        final RecyclerView contactsView = this.findViewById(R.id.call_ongoing_contacts);

        final List<Friend> friends = Collections.singletonList(
                new Friend(
                        -1,
                        holder.friendTitle,
                        "Trying the TokTok",
                        0,
                        Color.parseColor("#DC4254"),
                        Color.parseColor("#7D2530"),
                        holder.friendImgSrc
                )
        );

        LinearLayoutManager contactsLayoutManager = new LinearLayoutManager(getBaseContext());
        contactsView.setLayoutManager(contactsLayoutManager);
        contactsView.setAdapter(new CallOnGoingContactsAdapter(friends));

        FloatingActionButton callOngoingFab = this.findViewById(R.id.call_ongoing_fab);
        callOngoingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initReceiveCall(final CallActivityViewHolder holder) {
        holder.topPanel.addView(getLayoutInflater().inflate(R.layout.call_top_receive, null, false));
        holder.bottomPanel.addView(getLayoutInflater().inflate(R.layout.call_bottom_receive, null, false));

        CircleImageView friendPhotoView = this.findViewById(R.id.call_img);
        friendPhotoView.setImageResource(holder.friendImgSrc);

        TextView callFriend = this.findViewById(R.id.call_friend);
        callFriend.setText(holder.friendTitle);
        EditText callMessageInput = this.findViewById(R.id.call_message_input);
        callMessageInput.setHint(getResources().getString(R.string.call_input_message) + " " + holder.friendTitle);

        RecyclerView callMessageView = this.findViewById(R.id.call_messages_recycler);
        List<String> excuses = Arrays.asList(
                "Sorry I’m In Class, Call you later",
                "I’m at a meeting, can’t talk",
                "Call you later",
                "I’m driving, call you when I stop",
                "Not available"
        );

        LinearLayoutManager callMessageLayoutManager = new LinearLayoutManager(getBaseContext());
        callMessageView.setLayoutManager(callMessageLayoutManager);
        callMessageView.setAdapter(new CallMessageAdapter(excuses));

        final CallSliderAnswer callAnswerSlider = this.findViewById(R.id.call_answer);
        final CallSliderDecline callDeclineSlider = this.findViewById(R.id.call_decline);

        callAnswerSlider.setOnCallListener(new CallListener() {
            @Override
            public void onCompleted() {
                switch (viewType) {
                    case 0:
                        Animation fadeOutAnimationBottom = AnimationUtils.loadAnimation(holder.bottomPanel.getContext(), R.anim.abc_fade_out);
                        fadeOutAnimationBottom.setDuration(250);
                        fadeOutAnimationBottom.setAnimationListener(new AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                holder.bottomPanel.removeAllViews();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });

                        holder.bottomPanel.getChildAt(0).startAnimation(fadeOutAnimationBottom);

                        Animation fadeOutAnimationTop = AnimationUtils.loadAnimation(holder.bottomPanel.getContext(), R.anim.abc_fade_out);
                        fadeOutAnimationTop.setDuration(250);
                        fadeOutAnimationTop.setAnimationListener(new AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                holder.topPanel.removeAllViews();
                                initOnGoingCall(holder);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });

                        holder.topPanel.getChildAt(0).startAnimation(fadeOutAnimationTop);
                        break;

                    case 2:
                        startActivity(new Intent(CallActivity.this, VideoCallActivity.class).putExtras(holder.bundle));
                        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                        finish();
                        break;
                }
            }

            @Override
            public void onStart() {
                Animation animation = AnimationUtils.loadAnimation(callDeclineSlider.getContext(), R.anim.abc_fade_out);
                animation.setDuration(250);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        callDeclineSlider.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                callDeclineSlider.startAnimation(animation);
            }

            @Override
            public void onReleased() {
                Animation animation = AnimationUtils.loadAnimation(callDeclineSlider.getContext(), R.anim.abc_fade_in);
                animation.setDuration(250);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        callDeclineSlider.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                callDeclineSlider.startAnimation(animation);
            }
        });

        callDeclineSlider.setOnCallListener(new CallListener() {

            @Override
            public void onCompleted() {
                finish();
            }

            @Override
            public void onReleased() {
                Animation animation = AnimationUtils.loadAnimation(callAnswerSlider.getContext(), R.anim.abc_fade_in);
                animation.setDuration(250);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        callAnswerSlider.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                callAnswerSlider.startAnimation(animation);
            }

            @Override
            public void onStart() {
                Animation animation = AnimationUtils.loadAnimation(callAnswerSlider.getContext(), R.anim.abc_fade_out);
                animation.setDuration(250);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        callAnswerSlider.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                callAnswerSlider.startAnimation(animation);
            }
        });

        TextView bottomTextBar = this.findViewById(R.id.call_message_bottom_bar);

        bottomTextBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.debug("asda");
            }
        });
    }

    private void initBackground(CallActivityViewHolder holder, int imgResource) {
        final ImageView background = this.findViewById(R.id.call_background);
        background.setImageResource(imgResource);

        Display screen = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        screen.getSize(screenSize);

        View content = findViewById(android.R.id.content).getRootView();

        content.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!backgroundInitialised) {
                    Bitmap picture = BlurBuilder.blur(background);
                    background.setImageDrawable(new BitmapDrawable(getResources(), picture));
                    backgroundInitialised = true;
                }
            }
        });

        double midHeight = screenSize.y * 0.5;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) midHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        holder.bottomPanel.setLayoutParams(params);
        holder.topPanel.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) midHeight));
    }

    @Override
    public void onBackPressed() {
    }

}

final class CallActivityViewHolder {
    final RelativeLayout topPanel;
    final FrameLayout bottomPanel;

    final Bundle bundle;
    final String friendTitle;
    final int friendColor;
    final int friendImgSrc;

    CallActivityViewHolder(CallActivity activity) {
        topPanel = activity.findViewById(R.id.call_top_panel);
        bottomPanel = activity.findViewById(R.id.call_bottom_panel);

        bundle = activity.getIntent().getExtras();
        friendTitle = BundleKey.contactName().get(bundle);
        friendColor = BundleKey.contactColorPrimary().get(bundle);
        friendImgSrc = BundleKey.contactPhotoReference().get(bundle);
    }
}

interface CallListener {
    void onCompleted();

    void onStart();

    void onReleased();
}
