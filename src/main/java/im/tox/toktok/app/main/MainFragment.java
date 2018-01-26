package im.tox.toktok.app.main;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

import im.tox.toktok.R;
import im.tox.toktok.app.CustomViewPager;
import im.tox.toktok.app.MainActivityHolder;
import im.tox.toktok.app.new_message.NewMessageActivity;
import im.tox.toktok.app.simple_dialogs.SimpleAddFriendDialogDesign;

public final class MainFragment extends Fragment {

    private CustomViewPager mViewPaper = null;
    private Menu mMenu = null;
    private Toolbar mToolbar = null;
    private TabLayout mTabs = null;
    private FloatingActionButton mFab = null;
    private MainTabsAdapter mPagerAdapter = null;
    private DrawerLayout mDrawer = null;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setHasOptionsMenu(true);
    }

    public FrameLayout onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreate(savedState);
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.activity_main_fragment, container, false);

        mDrawer = getActivity().findViewById(R.id.home_layout);

        initToolbar(view);
        initFAB(view);
        initViewPaper(view);

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initViewPaper(FrameLayout view) {
        mViewPaper = view.findViewById(R.id.home_tab_holder);
        mPagerAdapter = new MainTabsAdapter(getChildFragmentManager(), getActivity());
        mViewPaper.setAdapter(mPagerAdapter);

        mViewPaper.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mFab.hide();
                    if (mMenu != null) {
                        mMenu.findItem(R.id.action_add_friend).setVisible(true);
                    }
                } else {
                    mFab.show();
                    if (mMenu != null) {
                        mMenu.findItem(R.id.action_add_friend).setVisible(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mTabs = view.findViewById(R.id.home_tabs);

        mTabs.setupWithViewPager(mViewPaper);
        mViewPaper.setCurrentItem(1);
    }

    private void initToolbar(FrameLayout view) {
        mToolbar = view.findViewById(R.id.home_toolbar);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.homeColorStatusBar, null));

        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initFAB(FrameLayout view) {
        mFab = view.findViewById(R.id.home_fab);
        mFab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewMessageActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }

        if (id == R.id.action_add_friend) {
            SimpleAddFriendDialogDesign dial = new SimpleAddFriendDialogDesign(getActivity(), null);
            dial.show();
            return true;
        }

        if (id == R.id.action_search) {
            HomeSearch searchLayout = (HomeSearch) getActivity().getLayoutInflater().inflate(
                    R.layout.home_search, null, false);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    PixelFormat.TRANSLUCENT
            );
            try {
                MainActivityHolder activity = (MainActivityHolder) getActivity();
                activity.getWindowManager().addView(searchLayout, params);
                activity.setSearch(searchLayout);
            } catch (WindowManager.BadTokenException e) {
                Log.e("MOMO", "exception: " + e);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

