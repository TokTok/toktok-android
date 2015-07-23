package im.tox.toktok.app.MainActivity

import android.content.Context
import android.support.v4.app.{Fragment, FragmentManager, FragmentStatePagerAdapter}
import im.tox.toktok.R
import im.tox.toktok.app.MainActivity.MainChatsFragment.ChatsFragment
import im.tox.toktok.app.MainActivity.MainFriendsFragment.FriendsFragment

class MainTabsAdapter(fm: FragmentManager, context: Context) extends FragmentStatePagerAdapter(fm) {

  var this.context = context

  override def getItem(i: Int): Fragment = {

    i match {
      case 1 => return new ChatsFragment()
      case 0 => return new FriendsFragment()
    }

    return null

  }

  override def getCount(): Int = {
    return 2
  }

  override def getPageTitle(i: Int): CharSequence = {
    i match {
      case 1 => return context.getResources.getString(R.string.home_tabs_chats)
      case 0 => return context.getResources.getString(R.string.home_tabs_friends)
    }
  }

}
