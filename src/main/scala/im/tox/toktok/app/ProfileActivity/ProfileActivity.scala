package im.tox.toktok.app.ProfileActivity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View.OnClickListener
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget.LinearLayout
import im.tox.toktok.R
import im.tox.toktok.app.SimpleDialogs.{SimpleStatusDialogDesign, SimpleTextDialogDesign, SimpleShareDialogDesign}

class ProfileActivity extends Fragment {

  var mToolbar: Toolbar = null
  var mDrawer: DrawerLayout = null

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    super.onCreate(savedState)
    val view: View = inflater.inflate(R.layout.activity_profile, container, false)

    getActivity.getWindow().setStatusBarColor(Color.parseColor("#2b000000"));

    initToolbar(view)

    val mCollapsingToolbar: CollapsingToolbarLayout = view.findViewById(R.id.profile_collapsing_toolbar).asInstanceOf[CollapsingToolbarLayout]
    mCollapsingToolbar.setTitle("André Almeida")

    mDrawer = getActivity.findViewById(R.id.home_layout).asInstanceOf[DrawerLayout]

    val shareIDButton = view.findViewById(R.id.profile_share_id).asInstanceOf[LinearLayout]

    shareIDButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val dial: SimpleShareDialogDesign = new SimpleShareDialogDesign(getActivity)
        dial.show()

      }
    })

    val changeNickname = view.findViewById(R.id.profile_change_status_text).asInstanceOf[LinearLayout]

    changeNickname.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val dial: SimpleTextDialogDesign = new SimpleTextDialogDesign(getActivity, "Edit your status message", R.drawable.ic_person_black_48dp, "Trying out the new Tox Android Clientww", null)
        dial.show()

      }
    })

    val changeStatusMessage = view.findViewById(R.id.profile_change_nickname).asInstanceOf[LinearLayout]

    changeStatusMessage.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val dial: SimpleTextDialogDesign = new SimpleTextDialogDesign(getActivity, "Edit your nickname", R.drawable.ic_person_black_48dp, "André Almeida", null)
        dial.show()

      }
    })

    val changeStatus = view.findViewById(R.id.profile_change_status).asInstanceOf[LinearLayout]

    changeStatus.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val dial : SimpleStatusDialogDesign =  new SimpleStatusDialogDesign(getActivity)
        dial.show()

      }
    })

    return view


  }

  def initToolbar(view: View): Unit = {

    mToolbar = view.findViewById(R.id.profile_toolbar).asInstanceOf[Toolbar]
    mToolbar.setTitle("André Almeida")

    getActivity.asInstanceOf[AppCompatActivity].setSupportActionBar(mToolbar)
    getActivity.asInstanceOf[AppCompatActivity].getSupportActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_menu)
    getActivity.asInstanceOf[AppCompatActivity].getSupportActionBar.setDisplayHomeAsUpEnabled(true);

    mToolbar.setNavigationOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        mDrawer.openDrawer(GravityCompat.START)
      }
    })

  }

}
