package im.tox.toktok.app.MessageActivity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.{FragmentPagerAdapter, FragmentManager, Fragment}
import android.support.v4.view.ViewPager
import android.view.{View, ViewGroup, LayoutInflater}
import im.tox.toktok.R
import im.tox.toktok.app.MainActivity.MainChatsFragment.ChatsFragment
import im.tox.toktok.app.MainActivity.MainFriendsFragment.FriendsFragment


class MessageAttachments extends Fragment {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.fragment_attachments, container, false);

    return view
  }

}