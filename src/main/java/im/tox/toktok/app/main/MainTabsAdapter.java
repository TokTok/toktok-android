package im.tox.toktok.app.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.main.chats.ChatsFragment;
import im.tox.toktok.app.main.friends.FriendsFragment;

public final class MainTabsAdapter extends FragmentStatePagerAdapter {

    private static final class Pair {
        private final Fragment fragment;
        private final int titleResource;

        Pair(Fragment fragment, int titleResource) {
            this.fragment = fragment;
            this.titleResource = titleResource;
        }
    }

    private final Context context;

    MainTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    private final List<Pair> items = Arrays.asList(
            new Pair(new FriendsFragment(), R.string.home_tabs_friends),
            new Pair(new ChatsFragment(), R.string.home_tabs_chats)
    );

    @Override
    public Fragment getItem(int i) {
        return items.get(i).fragment;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return context.getResources().getString(items.get(i).titleResource);
    }

}
