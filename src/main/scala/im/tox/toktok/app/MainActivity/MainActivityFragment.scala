package im.tox.toktok.app.MainActivity

import android.content.{Context, Intent}
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.design.widget.{FloatingActionButton, TabLayout}
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View.{OnLayoutChangeListener, OnClickListener}
import android.view.ViewGroup.LayoutParams
import android.view._
import im.tox.toktok.R
import im.tox.toktok.app.{MainActivityHolder, CustomViewPager}
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

    mToolbar = view.findViewById(R.id.home_toolbar).asInstanceOf[Toolbar]

    getActivity.getWindow().setStatusBarColor(getResources.getColor(R.color.homeColorStatusBar));


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

      case R.id.action_add_friend => {
        val dial = new SimpleAddFriendDialogDesign(getActivity, null)
        dial.show()
        return true
      }

      case R.id.action_search => {

        val searchLayout = getActivity.getLayoutInflater.inflate(R.layout.home_search, null).asInstanceOf[HomeSearch]
        val params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, PixelFormat.TRANSLUCENT)
        val window = getActivity.getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]

        window.addView(searchLayout, params)

        getActivity.asInstanceOf[MainActivityHolder].setSearch(searchLayout)

        return true
      }

    }

    return super.onOptionsItemSelected(item)

  }

}

