package im.tox.toktok.app.MainActivity

import android.support.v4.app.{Fragment, FragmentManager, FragmentPagerAdapter}
import im.tox.toktok.app.MainChatsFragment.ChatsFragment
import im.tox.toktok.app.MainFriendsFragment.FriendsFragment

class MainTabsAdapter(fm: FragmentManager) extends FragmentPagerAdapter(fm) {

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
      case 1 => return "Chats"
      case 0 => return "Friends"
    }
  }

}
