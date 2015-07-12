package im.tox.toktok.app.MessageActivity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.{FragmentPagerAdapter, FragmentManager, Fragment}
import android.support.v4.view.ViewPager
import android.view.{View, ViewGroup, LayoutInflater}
import im.tox.toktok.R
import im.tox.toktok.app.MainChatsFragment.ChatsFragment
import im.tox.toktok.app.MainFriendsFragment.FriendsFragment


class MessageAttachments extends Fragment {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.fragment_attachments, container, false);

    val mViewPaper: ViewPager = view.findViewById(R.id.fragment_viewpager).asInstanceOf[ViewPager]
    val mViewPaperAdapter: MessageAttachmentsTabsAdapter = new MessageAttachmentsTabsAdapter(getChildFragmentManager)
    mViewPaper.setAdapter(mViewPaperAdapter)

    val mTabs: TabLayout = view.findViewById(R.id.fragment_tabs).asInstanceOf[TabLayout]
    mTabs.setupWithViewPager(mViewPaper)

    return view
  }

}

class MessageAttachmentsTabsAdapter(fm: FragmentManager) extends FragmentPagerAdapter(fm) {

  override def getItem(i: Int): Fragment = {

    i match {
      case 0 => return new MessageFragmentItems
      case 1 => return new MessageFragmentsAttachmentsList
    }

    return null

  }

  override def getCount(): Int = {
    return 2
  }

  override def getPageTitle(i: Int): CharSequence = {
    i match {
      case 1 => return "Attachments"
      case 0 => return "Add Items"
    }
  }

}