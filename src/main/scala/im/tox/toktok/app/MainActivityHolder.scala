package im.tox.toktok.app

import android.os.Bundle
import android.support.v4.app.{ Fragment, FragmentManager }
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import com.typesafe.scalalogging.Logger
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.main.friends.SlideInContactsLayout
import im.tox.toktok.app.main.{ HomeSearch, MainFragment }
import im.tox.toktok.app.profile.ProfileFragment
import im.tox.toktok.{ R, TR }
import org.scaloid.common._
import org.slf4j.LoggerFactory

final class MainActivityHolder extends AppCompatActivity {

  private val logger = Logger(LoggerFactory.getLogger(getClass))

  private var activeTab: LinearLayout = null
  private var activeContacts: SlideInContactsLayout = null
  private var activeSearch: HomeSearch = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val chatsDrawerItem = this.findView(TR.home_drawer_chats)
    val profileDrawerItem = this.findView(TR.home_drawer_profile)
    val settingsDrawerItem = this.findView(TR.home_drawer_settings)

    chatsDrawerItem.onClick { (v: View) =>
      activeTab = changeTab(activeTab, v.asInstanceOf[LinearLayout], "Chats", "", new MainFragment)
    }

    profileDrawerItem.onClick { (v: View) =>
      activeTab = changeTab(activeTab, v.asInstanceOf[LinearLayout], "Profile", "Activity", new ProfileFragment)
    }

    chatsDrawerItem.callOnClick()
  }

  override def onDestroy(): Unit = {
    if (activeSearch != null) {
      getWindowManager.removeView(activeSearch)
      activeSearch = null
    }

    if (activeContacts != null) {
      getWindowManager.removeView(activeContacts)
      activeContacts = null
    }

    super.onDestroy()
  }

  private def changeTab[T <: Fragment](
    oldTab: LinearLayout,
    newTab: LinearLayout,
    tag: String,
    stackName: String,
    newFragment: => T
  ): LinearLayout = {
    if (newTab != oldTab) {
      getSupportFragmentManager
        .beginTransaction()
        .replace(R.id.home_frame, newFragment, tag)
        .addToBackStack(stackName)
        .commit()
    }

    if (oldTab != null) {
      oldTab.setBackgroundResource(R.drawable.background_ripple)
    }

    newTab.setBackgroundResource(R.color.drawerBackgroundSelected)
    this.findView(TR.home_layout).closeDrawers()

    newTab
  }

  def setAddContactPopup(contact: SlideInContactsLayout): Unit = {
    activeContacts = contact
  }

  def setSearch(homeSearch: HomeSearch): Unit = {
    activeSearch = homeSearch
  }

  override def onBackPressed(): Unit = {
    if (getSupportFragmentManager.findFragmentByTag("Profile") != null) {
      getSupportFragmentManager.popBackStack("Activity", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    if (activeContacts != null) {
      activeContacts.finish {
        getWindowManager.removeView(activeContacts)
        activeContacts = null
      }
    }

    if (activeSearch != null) {
      activeSearch.finish {
        getWindowManager.removeView(activeSearch)
        activeSearch = null
      }
    }
  }

}
