package im.tox.toktok.app

import android.os.Bundle
import android.support.v4.app.{FragmentTransaction, Fragment}
import android.support.v7.app.AppCompatActivity
import im.tox.toktok.R
import im.tox.toktok.app.MainActivity.MainActivityFragment

class MainActivityHolder extends AppCompatActivity {

  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val attachFragment: Fragment = new MainActivityFragment
    val trans: FragmentTransaction = getSupportFragmentManager.beginTransaction()
    trans.add(R.id.home_frame, attachFragment)
    trans.commit()

  }

}