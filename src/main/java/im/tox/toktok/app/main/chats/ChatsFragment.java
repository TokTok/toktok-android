package im.tox.toktok.app.main.chats;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.CustomViewPager;
import im.tox.toktok.app.MyRecyclerScroll;
import im.tox.toktok.app.domain.ChatMessage;
import im.tox.toktok.app.new_message.NewMessageActivity;

public final class ChatsFragment extends Fragment implements ChatItemClick {

    @Nullable
    private RecyclerView mChatsRecycler = null;
    @Nullable
    private ChatsRecyclerAdapter mChatsRecyclerAdapter = null;
    @Nullable
    private ActionMode mActionMode = null;

    @Override
    public FrameLayout onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_home_chats, container, false);
        final FloatingActionButton fab = ((AppCompatActivity) getActivity()).findViewById(R.id.home_fab);

        // Recycler View

        mChatsRecycler = view.findViewById(R.id.home_chats_recycler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mChatsRecycler.setLayoutManager(mLayoutManager);

        List<ChatMessage> chatMessages = Arrays.asList(
                ChatMessage.loremMessage,
                ChatMessage.johnMessage,
                ChatMessage.groupMessage
        );

        mChatsRecyclerAdapter = new ChatsRecyclerAdapter(chatMessages, this);

        mChatsRecycler.setAdapter(mChatsRecyclerAdapter);
        mChatsRecycler.setItemAnimator(new DefaultItemAnimator());
        mChatsRecycler.addOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void hide() {
                fab.animate().translationY(fab.getHeight() + fab.getBottom()).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            @Override
            public void show() {
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });

        return view;
    }

    private void toggleSelection(int i) {
        mChatsRecyclerAdapter.toggleSelection(i);

        int count = mChatsRecyclerAdapter.getSelectedItemCount();
        switch (count) {
            case 0:
                mActionMode.finish();
                break;
            case 1:
                mActionMode.setTitle(getResources().getString(R.string.action_mode_selected_single));
                break;
            default:
                mActionMode.setTitle(count + " " + getResources().getString(R.string.action_mode_selected_multi));
        }
    }

    @Override
    public boolean onClick(int i) {
        if (mActionMode == null) {
            return false;
        }
        toggleSelection(i);
        return true;
    }

    @Override
    public boolean onLongClick(int i) {
        if (mActionMode == null) {
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ChatsActionModeCallback());
        }

        toggleSelection(i);
        return true;
    }

    final class ChatsActionModeCallback implements ActionMode.Callback {
        @Nullable
        private AppBarLayout mAppLayout = null;
        @Nullable
        private FloatingActionButton mFab = null;
        @Nullable
        private CustomViewPager mCustomViewPager = null;

        @Override
        public boolean onCreateActionMode(@NonNull final ActionMode mode, Menu menu) {
            final AppCompatActivity activity = (AppCompatActivity) getActivity();
            mAppLayout = activity.findViewById(R.id.appBarLayout);
            mAppLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor, null));

            mFab = activity.findViewById(R.id.home_fab);
            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.textDarkColor, null)));
            mFab.setImageResource(R.drawable.ic_action_delete);
            mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.textWhiteColor, null)));

            mCustomViewPager = activity.findViewById(R.id.home_tab_holder);
            mCustomViewPager.setSwipingEnabled(false);

            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mChatsRecyclerAdapter.deleteSelected();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mode.finish();
                        }
                    }, 500);
                }
            });

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAppLayout.setBackgroundColor(getResources().getColor(R.color.homeColorToolbar, null));

            mFab.setImageResource(R.drawable.ic_content_add_home);
            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.basicFABColor, null)));
            mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.basicFABTint, null)));

            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), NewMessageActivity.class));
                }
            });

            mCustomViewPager.setSwipingEnabled(true);

            mChatsRecyclerAdapter.clearSelections();
            mActionMode = null;
            mAppLayout = null;
            mFab = null;
            mCustomViewPager = null;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

    }

}
