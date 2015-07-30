package im.tox.toktok.app

import android.os.Bundle
import android.support.v4.app.{Fragment, FragmentTransaction}
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import im.tox.toktok.R
import im.tox.toktok.app.MainActivity.MainActivityFragment
import im.tox.toktok.app.ProfileActivity.ProfileActivity

class MainActivityHolder extends AppCompatActivity {

  var activeTab: LinearLayout = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)

    val attachFragment: Fragment = new MainActivityFragment

    getSupportFragmentManager.beginTransaction().add(R.id.home_frame, attachFragment).commit()

    val chatsTabButton: LinearLayout = findViewById(R.id.home_drawer_chats).asInstanceOf[LinearLayout]
    val peopleTabButton: LinearLayout = findViewById(R.id.home_drawer_profile).asInstanceOf[LinearLayout]
    val settingsTabButton: LinearLayout = findViewById(R.id.home_drawer_settings).asInstanceOf[LinearLayout]

    activeTab = chatsTabButton

    activeTab.setBackgroundResource(R.color.drawerBackgroundSelected)



    peopleTabButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        if (v != activeTab) {
          val profileFragment: ProfileActivity = new ProfileActivity

          val trans: FragmentTransaction = getSupportFragmentManager.beginTransaction()
          trans.replace(R.id.home_frame, profileFragment)
          trans.addToBackStack(null)
          trans.commit()
          changeTab(v)
        }
        else {
          findViewById(R.id.home_layout).asInstanceOf[DrawerLayout].closeDrawers()
        }
      }
    })

    chatsTabButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        if (v != activeTab) {
          val fragment: Fragment = new MainActivityFragment

          val trans: FragmentTransaction = getSupportFragmentManager.beginTransaction()
          trans.replace(R.id.home_frame, fragment)
          trans.addToBackStack(null)
          trans.commit()
          changeTab(v)
        }
        else {
          findViewById(R.id.home_layout).asInstanceOf[DrawerLayout].closeDrawers()
        }
      }
    })

    def changeTab(v: View): Unit = {

      activeTab.setBackgroundResource(R.drawable.background_ripple)
      activeTab = v.asInstanceOf[LinearLayout]
      activeTab.setBackgroundResource(R.color.drawerBackgroundSelected)
      findViewById(R.id.home_layout).asInstanceOf[DrawerLayout].closeDrawers()


    }

  }

}