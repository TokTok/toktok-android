package im.tox.toktok.app.profile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup.LayoutParams
import android.view.{ LayoutInflater, View, ViewGroup }
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.simple_dialogs.{ SimpleShareDialogDesign, SimpleStatusDialogDesign, SimpleTextDialogDesign }
import im.tox.toktok.{ R, TR }

final class ProfileFragment extends Fragment {

  private implicit def activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): CoordinatorLayout = {
    super.onCreate(savedState)
    val view = inflater.inflate(TR.layout.activity_profile, container, false)

    getActivity.getWindow.setStatusBarColor(Color.parseColor("#2b000000"))

    view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

    val toolbar = view.findView(TR.profile_toolbar)
    activity.setSupportActionBar(toolbar)
    activity.getSupportActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_menu)
    activity.getSupportActionBar.setDisplayHomeAsUpEnabled(true)

    val drawer = getActivity.findView(TR.home_layout)
    toolbar.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        drawer.openDrawer(GravityCompat.START)
      }
    })

    val mShareIDButton = view.findView(TR.profile_share_id)
    mShareIDButton.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleShareDialogDesign(getActivity)
        dial.show()
      }
    })

    val mChangeNickname = view.findView(TR.profile_change_nickname)
    mChangeNickname.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleTextDialogDesign(
          getActivity,
          getResources.getString(R.string.profile_nickname),
          getResources.getColor(R.color.homeColorToolbar, null),
          R.drawable.ic_person_black_48dp,
          getResources.getString(R.string.sample_user_name),
          null
        )
        dial.show()
      }
    })

    val mChangeStatusMessage = view.findView(TR.profile_change_status_text)
    mChangeStatusMessage.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleTextDialogDesign(
          getActivity,
          getResources.getString(R.string.profile_status_message),
          getResources.getColor(R.color.homeColorToolbar, null),
          R.drawable.ic_person_black_48dp,
          getResources.getString(R.string.sample_status_message),
          null
        )
        dial.show()
      }
    })

    val mChangeStatus = view.findView(TR.profile_change_status)
    mChangeStatus.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        val dial = new SimpleStatusDialogDesign(getActivity, 0)
        dial.show()
      }
    })

    val mChangeReject = view.findView(TR.profile_change_reject_call)
    mChangeReject.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View): Unit = {
        startActivity(new Intent(activity, classOf[RejectedCallMessages]))
      }
    })

    view
  }

}
