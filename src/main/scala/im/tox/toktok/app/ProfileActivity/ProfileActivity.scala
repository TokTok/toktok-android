package im.tox.toktok.app.ProfileActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.{MenuItem, LayoutInflater, View, ViewGroup}
import android.widget.{RelativeLayout, TextView}
import im.tox.toktok.R
import im.tox.toktok.app.SimpleDialogs.{SimpleAddFriendDialogDesign, SimpleShareDialogDesign, SimpleStatusDialogDesign, SimpleTextDialogDesign}

class ProfileActivity extends Fragment {

  var mToolbar: Toolbar = null
  var mDrawer: DrawerLayout = null

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    super.onCreate(savedState)
    val view: View = inflater.inflate(R.layout.activity_profile, container, false)

    getActivity.getWindow().setStatusBarColor(Color.parseColor("#2b000000"));

    view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))

    initToolbar(view)

    val mCollapsingToolbar: CollapsingToolbarLayout = view.findViewById(R.id.profile_collapsing_toolbar).asInstanceOf[CollapsingToolbarLayout]

    mDrawer = getActivity.findViewById(R.id.home_layout).asInstanceOf[DrawerLayout]

    val mShareIDButton = view.findViewById(R.id.profile_share_id).asInstanceOf[RelativeLayout]

    mShareIDButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val dial: SimpleShareDialogDesign = new SimpleShareDialogDesign(getActivity)
        dial.show()

      }
    })

    val mChangeNickname = view.findViewById(R.id.profile_change_nickname).asInstanceOf[TextView]

    mChangeNickname.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleTextDialogDesign = new SimpleTextDialogDesign(getActivity, "Edit your nickname", getResources.getColor(R.color.homeColorToolbar), R.drawable.ic_person_black_48dp, "André Almeida", null)
        dial.show()

      }
    })

    val mChangeStatusMessage = view.findViewById(R.id.profile_change_status_text).asInstanceOf[TextView]

    mChangeStatusMessage.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val dial: SimpleTextDialogDesign = new SimpleTextDialogDesign(getActivity, "Edit your status message", getResources.getColor(R.color.homeColorToolbar), R.drawable.ic_person_black_48dp, "Trying out the new Tox Android Clientww", null)
        dial.show()

      }
    })

    val mChangeStatus = view.findViewById(R.id.profile_change_status).asInstanceOf[TextView]

    mChangeStatus.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val dial: SimpleStatusDialogDesign = new SimpleStatusDialogDesign(getActivity,0)
        dial.show()

      }
    })


    val mChangeReject = view.findViewById(R.id.profile_change_reject_call).asInstanceOf[TextView]

    mChangeReject.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val rejectIntent = new Intent(getActivity,classOf[RejectedCallMessages])
        startActivity(rejectIntent)
      }
    })

    return view

  }

  def initToolbar(view: View): Unit = {

    mToolbar = view.findViewById(R.id.profile_toolbar).asInstanceOf[Toolbar]
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
