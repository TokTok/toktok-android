package im.tox.toktok.app

import android.os.{ Bundle, Handler }
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import com.typesafe.scalalogging.Logger
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.main.friends.SlideInContactsLayout
import im.tox.toktok.app.main.{ HomeSearch, MainFragment }
import im.tox.toktok.app.profile.ProfileFragment
import im.tox.toktok.{ R, TR }
import org.slf4j.LoggerFactory

final class MainActivityHolder extends AppCompatActivity {

  private val logger = Logger(LoggerFactory.getLogger(getClass))

  private var activeTab: LinearLayout = null
  private var activeContacts: SlideInContactsLayout = null
  private var activeSearch: HomeSearch = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    getSupportFragmentManager
      .beginTransaction()
      .replace(R.id.home_frame, new MainFragment, "Chats")
      .commit()

    val chatsDrawerItem = this.findView(TR.home_drawer_chats)
    val profileDrawerItem = this.findView(TR.home_drawer_profile)
    val settingsDrawerItem = this.findView(TR.home_drawer_settings)

    activeTab = chatsDrawerItem

    activeTab.setBackgroundResource(R.color.drawerBackgroundSelected)

    chatsDrawerItem.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        if (v != activeTab) {
          getSupportFragmentManager
            .beginTransaction()
            .replace(R.id.home_frame, new MainFragment, "Chats")
            .addToBackStack("")
            .commit()
          changeTab(v)
        } else {
          MainActivityHolder.this.findView(TR.home_layout).closeDrawers()
        }
      }
    })

    profileDrawerItem.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        if (v != activeTab) {
          getSupportFragmentManager
            .beginTransaction()
            .replace(R.id.home_frame, new ProfileFragment, "Profile")
            .addToBackStack("Activity")
            .commit()
          changeTab(v)
        } else {
          MainActivityHolder.this.findView(TR.home_layout).closeDrawers()
        }
      }
    })

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

  private def changeTab(v: View): Unit = {
    activeTab.setBackgroundResource(R.drawable.background_ripple)
    activeTab = v.asInstanceOf[LinearLayout]
    activeTab.setBackgroundResource(R.color.drawerBackgroundSelected)
    this.findView(TR.home_layout).closeDrawers()
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
