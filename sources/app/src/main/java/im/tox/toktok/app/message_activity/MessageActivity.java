package im.tox.toktok.app.message_activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.CompatUtil;
import im.tox.toktok.app.SizeAnimation;
import im.tox.toktok.app.contacts.FileSendActivity;
import im.tox.toktok.app.domain.Friend;
import im.tox.toktok.app.domain.Message;
import im.tox.toktok.app.domain.MessageType;
import im.tox.toktok.app.main.friends.SlideInContactsLayout;
import im.tox.toktok.app.simple_dialogs.SimpleDialogDesign;
import im.tox.toktok.app.simple_dialogs.SimpleTextDialogDesign;

import static im.tox.toktok.app.TypedBundleKey.SBundle;

public final class MessageActivity extends AppCompatActivity implements MessageClick, MessageActionMode {

    private static final Logger logger = LoggerFactory.getLogger(MessageActivity.class);

    private int contactColorPrimary = 0;
    private int contactColorStatus = 0;
    private int typeOfMessage = 0;
    @Nullable
    private FloatingActionButton mSendButton = null;
    @NonNull
    private Boolean mSendButtonActive = false;
    @Nullable
    private String title = "";
    private int imgSRC = 0;
    @Nullable
    private CardView mInputLayout = null;
    @Nullable
    private EditText mInput = null;
    @Nullable
    private MessageAdapter mRecyclerAdapter = null;
    @Nullable
    private RecyclerView mRecycler = null;
    @Nullable
    private SlideInAttachmentsLayout overlayAttachments = null;
    @Nullable
    private ActionMode mActionMode = null;
    @NonNull
    private final ActionMode.Callback actionModeCallback = new MessageActionModeCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Bundle bundle = getIntent().getExtras();
        contactColorPrimary = BundleKey.contactColorPrimary.get(bundle);
        contactColorStatus = BundleKey.contactColorStatus.get(bundle);
        typeOfMessage = BundleKey.messageType.get(bundle);
        title = BundleKey.messageTitle.get(bundle);
        imgSRC = BundleKey.imgResource.get(bundle);

        initToolbar();
        initRecyclerView();
        initInput();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_recall_message) {
            startActivity(new Intent(this, MessageRecallActivity.class)
                    .putExtras(SBundle(
                            BundleKey.colorPrimary.map(contactColorPrimary),
                            BundleKey.colorPrimaryStatus.map(contactColorStatus)
                    )));
            return true;
        }

        if (id == R.id.action_group_members) {
            startActivity(new Intent(this, MessageGroupContacts.class).putExtras(SBundle(
                    BundleKey.colorPrimary.map(contactColorPrimary),
                    BundleKey.colorPrimaryStatus.map(contactColorStatus)
            )));
            return true;
        }

        if (id == R.id.action_see_files_list) {
            startActivity(new Intent(this, FileSendActivity.class).putExtras(SBundle(
                    BundleKey.contactName.map(title),
                    BundleKey.contactColorPrimary.map(contactColorPrimary),
                    BundleKey.contactColorStatus.map(contactColorStatus)
            )));

            return true;
        }

        if (id == R.id.action_leave_conversation) {
            SimpleDialogDesign dial = new SimpleDialogDesign(
                    this,
                    getResources().getString(R.string.dialog_leave_group),
                    contactColorPrimary,
                    R.drawable.ic_delete_black_48dp,
                    null
            );
            dial.show();
            return true;
        }

        if (id == R.id.action_rename_conversation) {
            SimpleTextDialogDesign dial = new SimpleTextDialogDesign(
                    this,
                    getResources().getString(R.string.dialog_edit_group_name),
                    contactColorPrimary,
                    R.drawable.ic_editor_mode_edit_48,
                    title,
                    null
            );
            dial.show();
            return true;
        }

        if (id == R.id.action_mute_conversation_group) {
            Snackbar snack = Snackbar.make(
                    this.findViewById(R.id.coordinatorLayout),
                    getResources().getString(R.string.message_snackbar_group_muted),
                    Snackbar.LENGTH_LONG
            );
            snack.setAction(getResources().getString(R.string.action_undo), v -> logger.debug("asdsad"));
            snack.setActionTextColor(contactColorPrimary);
            View snackView = snack.getView();
            snackView.setBackgroundResource(R.color.snackBarColor);
            snackView.setElevation(10);
            TextView snackText = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
            snackText.setTextColor(CompatUtil.getColor(getResources(), R.color.textDarkColor));
            snack.show();
            return true;
        }

        if (id == R.id.action_delete_conversation) {
            SimpleDialogDesign dial = new SimpleDialogDesign(
                    this,
                    getResources().getString(R.string.dialog_delete_conversion),
                    contactColorPrimary,
                    R.drawable.ic_delete_black_48dp,
                    null
            );
            dial.show();
            return true;
        }

        if (id == R.id.action_mute_conversation_single) {
            Snackbar snack = Snackbar.make(
                    this.findViewById(R.id.coordinatorLayout),
                    getResources().getString(R.string.message_snackbar_friend_muted),
                    Snackbar.LENGTH_LONG
            );
            snack.setAction(getResources().getString(R.string.action_undo), v -> logger.debug("asdsad"));
            snack.setActionTextColor(contactColorPrimary);
            View snackView = snack.getView();
            snackView.setBackgroundResource(R.color.snackBarColor);
            snackView.setElevation(10);
            TextView snackText = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
            snackText.setTextColor(CompatUtil.getColor(getResources(), R.color.textDarkColor));
            snack.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (typeOfMessage == 0) {
            getMenuInflater().inflate(R.menu.menu_single_message, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_group_message, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        Toolbar mToolbar = this.findViewById(R.id.message_toolbar);
        mToolbar.setBackgroundColor(contactColorPrimary);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setStatusBarColor(contactColorStatus);

        overlayAttachments = this.findViewById(R.id.fragment_attachments_slide);
        RelativeLayout header = this.findViewById(R.id.message_header);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        switch (typeOfMessage) {
            case 0:
                header.addView(getLayoutInflater().inflate(R.layout.message_header_user, null, true), params);
                header.<CircleImageView>findViewById(R.id.message_header_user_img).setImageResource(imgSRC);
                header.setOnClickListener(v -> startOverLayFriend());
                break;

            default:
                header.addView(getLayoutInflater().inflate(R.layout.message_header_group, null, true), params);
                header.setOnClickListener(v -> startActivity(new Intent(MessageActivity.this, MessageGroupContacts.class).putExtras(SBundle(
                        BundleKey.colorPrimary.map(contactColorPrimary),
                        BundleKey.colorPrimaryStatus.map(contactColorStatus)
                ))));
        }

        TextView mUserName = header.findViewById(R.id.message_header_title);
        mUserName.setText(title);
    }

    private void initRecyclerView() {
        mRecycler = this.findViewById(R.id.message_recycler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        mLayoutManager.setReverseLayout(true);
        mRecycler.setHasFixedSize(true);
        mRecyclerAdapter = new MessageAdapter(this, this);

        if (imgSRC == 0) {
            imgSRC = R.drawable.lorem;
            mRecyclerAdapter.addItem(new Message(MessageType.Action, "The Amazing Group was created", "", R.drawable.user));
            mRecyclerAdapter.addItem(new Message(MessageType.Received, "Awesome stuff!", "14:29 Received", R.drawable.john));
        }

        mRecyclerAdapter.addItem(new Message(MessageType.Delivered, "Welcome to TokTok " + title + ", I hope you love it, as much as I do \uD83D\uDE00", "14:30 Delivered", R.drawable.user));
        mRecyclerAdapter.addItem(new Message(MessageType.Received, "Thanks André Almeida, let's hope soo.", "14:31 Received", imgSRC));
        mRecyclerAdapter.addItem(new Message(MessageType.Action, "Smiled", "", imgSRC));
        mRecyclerAdapter.addItem(new Message(MessageType.Received, "Yooo!", "14:32 Received", imgSRC));

        mRecycler.setAdapter(mRecyclerAdapter);
        mRecycler.setLayoutManager(mLayoutManager);

        mRecycler.setItemAnimator(new MessageItemAnimator());

        ImageButton attachButton = this.findViewById(R.id.message_attachments_button);

        attachButton.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) MessageActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken(), 0);

            overlayAttachments.start();
        });
    }

    private void initInput() {
        mInput = this.findViewById(R.id.message_input);
        mInputLayout = this.findViewById(R.id.message_input_card_view);
        mSendButton = this.findViewById(R.id.message_fab);
        mSendButton.setBackgroundTintList(ColorStateList.valueOf(contactColorPrimary));

        mSendButton.setOnClickListener(v -> {
            logger.debug("hahaha: " + mInput.getText());
            mRecyclerAdapter.addItem(new Message(MessageType.Delivered, mInput.getText().toString(), "14:41 Delivered", R.drawable.user));
            mRecycler.smoothScrollToPosition(0);
            mInput.setText("");
        });

        if (typeOfMessage == 0) {
            mInput.setHint(getResources().getString(R.string.message_hint_single) + " " + title);
        }

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textLength = mInput.getText().length();
                if (textLength == 0 && mSendButtonActive) {
                    expandInputBar();
                } else if (textLength > 0 && !mSendButtonActive) {
                    shrinkInputBar();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void shrinkInputBar() {
        SizeAnimation inputAnimation = new SizeAnimation(
                mInputLayout,
                mInputLayout.getWidth() - mSendButton.getWidth() - mSendButton.getPaddingRight(), 0
        );
        inputAnimation.setDuration(250);
        mInputLayout.startAnimation(inputAnimation);

        Animation buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_from_right);

        buttonAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mSendButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mSendButton.clearAnimation();
        mSendButton.startAnimation(buttonAnimation);
        mSendButtonActive = true;
    }

    private void expandInputBar() {
        // Expansion animation of the text input widget.
        SizeAnimation inputAnimation = new SizeAnimation(
                mInputLayout,
                mInputLayout.getWidth() + mSendButton.getWidth() + mSendButton.getPaddingRight(), 0
        );
        inputAnimation.setDuration(250);
        mInputLayout.startAnimation(inputAnimation);

        Animation buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_to_right);

        buttonAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Hide the button behind the input widget, because the setVisibility call performs another
                // fade-out animation that shows the button temporarily.
                mSendButton.setZ(-1);
                mSendButton.clearAnimation();
                mSendButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mSendButton.startAnimation(buttonAnimation);
        mSendButtonActive = false;
    }

    @Override
    public void onImgClick() {
        startOverLayFriend();
    }

    private void startOverLayFriend() {
        SlideInContactsLayout overlayContactsLayout = (SlideInContactsLayout) getLayoutInflater().inflate(
                R.layout.overlay_contacts, null, false);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                PixelFormat.TRANSLUCENT
        );
        getWindowManager().addView(overlayContactsLayout, params);

        TypedValue tv = new TypedValue();
        int actionBarHeight =
                getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true) ?
                        TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics()) : 0;

        overlayContactsLayout.start(this, Friend.lorem, actionBarHeight);
    }

    @Override
    public void onBackPressed() {
        if (overlayAttachments.getVisibility() == View.INVISIBLE) {
            finish();
        } else {
            overlayAttachments.finish();
        }
    }

    private void toggleSelection(int i) {
        mRecyclerAdapter.toggleSelection(i);

        final int count = mRecyclerAdapter.getSelectedItemCount();
        switch (count) {
            case 0:
                mActionMode.finish();
                break;
            case 1:
                mActionMode.setTitle(getResources().getString(R.string.action_mode_selected_single));
                break;
            default:
                mActionMode.setTitle(count + " " + getResources().getString(R.string.action_mode_selected_multi));
                break;
        }
    }

    @Override
    public void onClick(int i) {
        if (mActionMode != null) {
            toggleSelection(i);
        }
    }

    @Override
    public boolean onLongClick(int i) {
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(actionModeCallback);
        }

        toggleSelection(i);

        return true;
    }

    private final class MessageActionModeCallback implements ActionMode.Callback {

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mRecyclerAdapter.setActionModeActive(false);
            mRecyclerAdapter.clearSelections();
            Animation animationIn = AnimationUtils.loadAnimation(mInputLayout.getContext(), R.anim.abc_fade_in);
            mInputLayout.setVisibility(View.VISIBLE);
            mInputLayout.startAnimation(animationIn);
        }

        @Override
        public boolean onCreateActionMode(@NonNull ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.message_action_mode, menu);

            mRecyclerAdapter.setActionModeActive(true);
            mRecyclerAdapter.notifyDataSetChanged();

            Animation animationOut = AnimationUtils.loadAnimation(mInputLayout.getContext(), R.anim.abc_slide_out_bottom);
            mInputLayout.startAnimation(animationOut);
            mInputLayout.setVisibility(View.GONE);

            return true;
        }

        @Override
        public boolean onActionItemClicked(@NonNull ActionMode mode, @NonNull MenuItem item) {
            final int id = item.getItemId();
            if (id == R.id.action_message_delete) {
                mRecyclerAdapter.deleteSelected();
                mode.finish();
            }

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

    }

}
