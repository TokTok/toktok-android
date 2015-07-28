package im.tox.toktok.app.MainActivity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.{FloatingActionButton, TabLayout}
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v4.view.{GravityCompat, ViewPager}
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View.OnClickListener
import android.view._
import im.tox.toktok.R
import im.tox.toktok.app.CustomViewPager
import im.tox.toktok.app.NewMessageActivity.NewMessageActivity
import im.tox.toktok.app.SimpleDialogs.SimpleAddFriendDialogDesign

class MainActivityFragment extends Fragment {

  var mViewPaper: CustomViewPager = null
  var mMenu: Menu = null
  var mToolbar: Toolbar = null
  var mTabs: TabLayout = null
  var mFab: FloatingActionButton = null
  var mPagerAdapter: MainTabsAdapter = null
  var mDrawer: DrawerLayout = null


  override def onCreate(savedState: Bundle): Unit = {
    super.onCreate(savedState)
    setHasOptionsMenu(true)
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    super.onCreate(savedState)
    val view: View = inflater.inflate(R.layout.activity_main_fragment, container, false)

    initToolbar(view)
    initFAB(view)
    initViewPaper(view)

    mDrawer = getActivity.findViewById(R.id.home_layout).asInstanceOf[DrawerLayout]

    return view

  }


  override def onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = {
    mMenu = menu
    inflater.inflate(R.menu.menu_main, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }


  def initViewPaper(view: View): Unit = {

    mViewPaper = view.findViewById(R.id.home_tab_holder).asInstanceOf[CustomViewPager]
    mPagerAdapter = new MainTabsAdapter(getChildFragmentManager, getActivity);
    mViewPaper.setAdapter(mPagerAdapter)

    mViewPaper.addOnPageChangeListener(new OnPageChangeListener {

      override def onPageScrollStateChanged(state: Int): Unit = {}

      override def onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int): Unit = {}

      override def onPageSelected(position: Int): Unit = {

        if (position == 0) {

          mFab.hide()

          if (mMenu != null) {
            mMenu.findItem(R.id.action_add_friend).setVisible(true)

          }
        }
        else {

          mFab.show()

          if (mMenu != null) {
            mMenu.findItem(R.id.action_add_friend).setVisible(false)
          }

        }
      }
    })

    mTabs = view.findViewById(R.id.home_tabs).asInstanceOf[TabLayout]

    mTabs.setupWithViewPager(mViewPaper)

    mViewPaper.setCurrentItem(1)

  }

  def initToolbar(view: View): Unit = {

    getActivity.getWindow.setStatusBarColor(getResources.getColor(R.color.homeColorStatusBar))

    mToolbar = view.findViewById(R.id.home_toolbar).asInstanceOf[Toolbar]

    mToolbar.setNavigationOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        mDrawer.openDrawer(GravityCompat.START)
      }
    })

    getActivity.asInstanceOf[AppCompatActivity].setSupportActionBar(mToolbar)

    val mActionBar = getActivity.asInstanceOf[AppCompatActivity].getSupportActionBar
    mActionBar.setTitle(getResources.getString(R.string.app_name))
    mActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_menu)
    mActionBar.setDisplayHomeAsUpEnabled(true)


  }

  def initFAB(view: View): Unit = {

    mFab = view.findViewById(R.id.home_fab).asInstanceOf[FloatingActionButton]
    mFab.setOnClickListener(new OnClickListener {
      override def onClick(view: View): Unit = {
        startActivity(new Intent(getActivity, classOf[NewMessageActivity]))
      }
    })

  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {

    item.getItemId match {

      case android.R.id.home => {
        mDrawer.openDrawer(GravityCompat.START)
        return true
      }

      case action_add_friend => {
        val dial = new SimpleAddFriendDialogDesign(getActivity, null)
        dial.show()
        return true
      }

    }

    return super.onOptionsItemSelected(item)

  }

}

