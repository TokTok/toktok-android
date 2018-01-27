package im.tox.toktok.app.new_message;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.BundleKey;
import im.tox.toktok.R;
import im.tox.toktok.app.MyRecyclerScroll;
import im.tox.toktok.app.domain.Friend;

public final class NewMessageActivity extends AppCompatActivity {

    private LinearLayout mSelectedFriends = null;
    private NewMessageRecyclerHeaderAdapter mFriends_Recycler_Adapter = null;
    private FloatingActionButton mFab = null;
    private TextView mSelectedFriendsText = null;
    private CircleImageView mSelectedFriendsImg = null;
    private TextView mSelectedFriendsCounter = null;
    private ImageButton mSelectedFriendsButton = null;
    private TextView mSelectedMini = null;
    private Boolean mSelectedMiniExtended = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        Bundle bundle = getIntent().getExtras();

        int colorPrimary = 0;
        int colorStatus = 0;
        if (bundle != null) {
            colorPrimary = BundleKey.colorPrimary().get(bundle);
            colorStatus = BundleKey.colorPrimaryStatus().get(bundle);
        } else {
            colorPrimary = Color.parseColor("#F5A623");
            colorStatus = Color.parseColor("#8C5F14");
        }

        mFab = this.findViewById(R.id.new_message_fab);

        initToolbar(colorPrimary, colorStatus);
        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView mRecycler = this.findViewById(R.id.new_message_recycler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        List<Friend> friends = Arrays.asList(Friend.bart, Friend.jane, Friend.john, Friend.lorem);

        mFriends_Recycler_Adapter = new NewMessageRecyclerHeaderAdapter(friends, null);

        mFriends_Recycler_Adapter.listener = new FriendAddOnClick() {
            @Override
            public void onClickListener(int position) {
                mFriends_Recycler_Adapter.selectItem(position);
                selectItem(position);
            }
        };

        mRecycler.setAdapter(mFriends_Recycler_Adapter);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mFriends_Recycler_Adapter));

        mRecycler.addOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                mFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                mFab.animate().translationY(mFab.getHeight() + mFab.getBottom())
                        .setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });
    }

    void initToolbar(int colour, int secondColour) {
        mSelectedFriends = this.findViewById(R.id.new_message_selected_base);
        mSelectedFriendsImg = this.findViewById(R.id.new_message_selected_img);
        mSelectedFriendsText = this.findViewById(R.id.new_message_toolbar_selected_text);
        mSelectedFriendsButton = this.findViewById(R.id.new_message_toolbar_selected_button);

        Toolbar mToolbar = this.findViewById(R.id.newMessage_toolbar);
        mToolbar.setBackgroundColor(colour);

        this.findViewById(R.id.new_message_app_bar_layout).setBackgroundColor(colour);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.new_message_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout mAppBarLayout = this.findViewById(R.id.new_message_app_bar_layout);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(secondColour);

        EditText mSearchField = this.findViewById(R.id.new_message_search_field);

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFriends_Recycler_Adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void initFirstSelectedContacts(NewMessageRecyclerAdapter adapter) {
        mSelectedFriends.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int height = mSelectedFriends.getMeasuredHeight();

        mSelectedFriends.setVisibility(View.VISIBLE);
        mSelectedFriends.getLayoutParams().height = 1;

        Animation inAnimation = new Animation() {
            @Override
            protected void applyTransformation(float time, Transformation transf) {
                if (time == 1) {
                    mSelectedFriends.getLayoutParams().height = LayoutParams.MATCH_PARENT;
                } else {
                    mSelectedFriends.getLayoutParams().height = (int) (time * height);
                }
                mSelectedFriends.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        setOneSelectedContact(adapter);

        mFab.show();

        inAnimation.setDuration(4 * (int) (height / mSelectedFriends.getContext().getResources().getDisplayMetrics().density));
        mSelectedFriends.startAnimation(inAnimation);
    }

    void destroySelectedContacts() {
        final int height = mSelectedFriends.getMeasuredHeight();

        Animation outAnimation = new Animation() {
            @Override
            protected void applyTransformation(float time, Transformation transf) {
                if (time == 1) {
                    mSelectedFriends.setVisibility(View.GONE);
                } else {
                    mSelectedFriends.getLayoutParams().height = height - (int) (time * height);
                    mSelectedFriends.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        outAnimation.setDuration(4 * (int) (height / mSelectedFriends.getContext().getResources().getDisplayMetrics().density));
        mSelectedFriends.startAnimation(outAnimation);

        mFab.hide();
    }

    void destroySelectedContactsMini() {
        mSelectedMini.setVisibility(View.GONE);
        mSelectedMini.setText("");
        mSelectedMiniExtended = false;
    }

    void setOneSelectedContact(final NewMessageRecyclerAdapter adapter) {
        Friend first = adapter.getFirstSelected();

        if (mSelectedMiniExtended) {
            destroySelectedContactsMini();
        }

        mSelectedFriendsImg.setImageResource(first.photoReference);
        mSelectedFriendsText.setText(first.userName);
        mSelectedFriendsButton.setImageResource(R.drawable.ic_content_clear);
        mSelectedFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelectedList();
                destroySelectedContacts();
            }
        });
    }

    void setMultiSelectedContact(final NewMessageRecyclerAdapter adapter, int selectedFriends) {
        mSelectedFriendsCounter = this.findViewById(R.id.new_message_selected_counter);
        mSelectedFriendsCounter.setText(String.valueOf(selectedFriends));

        mSelectedFriendsText.setText(getResources().getString(R.string.new_message_selected_friends));
        mSelectedFriendsImg.setImageResource(R.color.backgroundColor);

        mSelectedFriendsButton.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
        mSelectedFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedMiniExtended) {
                    destroySelectedContactsMini();
                    mSelectedFriendsButton.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                } else {
                    mSelectedMini.setVisibility(View.VISIBLE);
                    mSelectedMiniExtended = true;
                    mSelectedFriendsButton.setImageResource(R.drawable.ic_hardware_keyboard_arrow_up);
                    createMiniContact(adapter);
                }
            }
        });
    }

    private void createMiniContact(final NewMessageRecyclerAdapter adapter) {
        CharSequence friendsList = "";

        for (final Friend friend : adapter.getSelectedFriends()) {
            SpannableStringBuilder sb = new SpannableStringBuilder();
            LinearLayout miniContact = createContactTextView(friend.userName);
            BitmapDrawable bd = convertViewToDrawable(miniContact);
            bd.setBounds(0, 0, bd.getIntrinsicWidth() * 3, bd.getIntrinsicHeight() * 3);

            sb.append(friend.userName + " ");
            sb.setSpan(new ImageSpan(bd),
                    sb.length() - (friend.userName.length() + 1),
                    sb.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mSelectedMini.setMovementMethod(LinkMovementMethod.getInstance());
            sb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    int i = 0;

                    for (Friend item : adapter.getItems()) {
                        if (item.id == friend.id) {
                            adapter.selectItem(i);
                            selectItem(i);
                        }
                        i += 1;
                    }
                }
            }, sb.length() - (friend.userName.length() + 1), sb.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            friendsList = TextUtils.concat(friendsList, sb);
        }

        mSelectedMini.setText(friendsList);
    }

    @NonNull
    private BitmapDrawable convertViewToDrawable(View view) {
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);

        view.setDrawingCacheEnabled(true);

        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);

        view.destroyDrawingCache();
        return new BitmapDrawable(null, viewBmp);
    }

    private LinearLayout createContactTextView(CharSequence text) {
        LinearLayout tv = (LinearLayout) getLayoutInflater().inflate(
                R.layout.new_message_toolbar_friend_mini, null, false);
        tv.<TextView>findViewById(R.id.new_message_friends_mini_text).setText(text);

        return tv;
    }

    private void selectItem(int position) {
        if (mSelectedFriends.getVisibility() == View.GONE) {
            initFirstSelectedContacts(mFriends_Recycler_Adapter);
        } else {
            int selectedFriends = mFriends_Recycler_Adapter.selectedCount();

            if (selectedFriends == 0) {

                destroySelectedContacts();

            } else if (selectedFriends == 1) {

                mSelectedFriendsCounter.setText("");
                mSelectedFriendsCounter = null;

                setOneSelectedContact(mFriends_Recycler_Adapter);

            } else {

                setMultiSelectedContact(mFriends_Recycler_Adapter, selectedFriends);
                mSelectedMini = this.findViewById(R.id.new_message_toolbar_mini);

                if (mSelectedMiniExtended) {
                    createMiniContact(mFriends_Recycler_Adapter);
                }
            }
        }
    }

}
