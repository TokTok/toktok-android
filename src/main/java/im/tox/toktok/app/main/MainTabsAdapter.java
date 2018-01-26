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
import scala.Tuple2;

public final class MainTabsAdapter extends FragmentStatePagerAdapter {

    private final Context context;

    MainTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    private final List<Tuple2<Fragment, Integer>> items = Arrays.asList(
            new Tuple2<Fragment, Integer>(new FriendsFragment(), R.string.home_tabs_friends),
            new Tuple2<Fragment, Integer>(new ChatsFragment(), R.string.home_tabs_chats)
    );

    public Fragment getItem(int i) {
        return items.get(i)._1;
    }

    public int getCount() {
        return items.size();
    }

    public CharSequence getPageTitle(int i) {
        return context.getResources().getString(items.get(i)._2);
    }

}
