package im.tox.toktok.app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import im.tox.toktok.R;
import im.tox.toktok.app.main.HomeSearch;
import im.tox.toktok.app.main.MainFragment;
import im.tox.toktok.app.main.friends.SlideInContactsLayout;
import im.tox.toktok.app.profile.ProfileFragment;

public final class MainActivityHolder extends AppCompatActivity {

    @Nullable
    private LinearLayout activeTab = null;
    @Nullable
    private SlideInContactsLayout activeContacts = null;
    @Nullable
    private HomeSearch activeSearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout chatsDrawerItem = this.findViewById(R.id.home_drawer_chats);
        final LinearLayout profileDrawerItem = this.findViewById(R.id.home_drawer_profile);

        chatsDrawerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTab = changeTab(activeTab, chatsDrawerItem, "Chats", "", new FragmentFactory() {
                    @NonNull
                    @Override
                    public Fragment get() {
                        return new MainFragment();
                    }
                });
            }
        });

        profileDrawerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTab = changeTab(activeTab, profileDrawerItem, "Profile", "Activity", new FragmentFactory() {
                    @NonNull
                    @Override
                    public Fragment get() {
                        return new ProfileFragment();
                    }
                });
            }
        });

        chatsDrawerItem.callOnClick();
    }

    @Override
    protected void onDestroy() {
        if (activeSearch != null) {
            getWindowManager().removeView(activeSearch);
            activeSearch = null;
        }

        if (activeContacts != null) {
            getWindowManager().removeView(activeContacts);
            activeContacts = null;
        }

        super.onDestroy();
    }

    @NonNull
    private <T extends Fragment> LinearLayout changeTab(
            @Nullable LinearLayout oldTab,
            @NonNull LinearLayout newTab,
            String tag,
            String stackName,
            @NonNull FragmentFactory<T> newFragment
    ) {
        if (newTab != oldTab) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_frame, newFragment.get(), tag)
                    .addToBackStack(stackName)
                    .commit();
        }

        if (oldTab != null) {
            oldTab.setBackgroundResource(R.drawable.background_ripple);
        }

        newTab.setBackgroundResource(R.color.drawerBackgroundSelected);
        DrawerLayout homeLayout = this.findViewById(R.id.home_layout);
        homeLayout.closeDrawers();

        return newTab;
    }

    public void setAddContactPopup(SlideInContactsLayout contact) {
        activeContacts = contact;
    }

    public void setSearch(HomeSearch homeSearch) {
        activeSearch = homeSearch;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("Profile") != null) {
            getSupportFragmentManager().popBackStack("Activity", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        if (activeContacts != null) {
            activeContacts.finish(new Runnable() {
                @Override
                public void run() {
                    getWindowManager().removeView(activeContacts);
                    activeContacts = null;
                }
            });
        }

        if (activeSearch != null) {
            activeSearch.finish(new Runnable() {
                @Override
                public void run() {
                    getWindowManager().removeView(activeSearch);
                    activeSearch = null;
                }
            });
        }
    }

    private interface FragmentFactory<T extends Fragment> {
        @NonNull
        T get();
    }
}
