package im.tox.toktok.app.profile

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup.LayoutParams
import android.view.{ LayoutInflater, View, ViewGroup }
import im.tox.toktok.TypedResource._
import im.tox.toktok.app.simple_dialogs.{ SimpleShareDialogDesign, SimpleStatusDialogDesign, SimpleTextDialogDesign }
import im.tox.toktok.{ R, TR }
import org.scaloid.common._

final class ProfileFragment extends Fragment {

  private implicit def activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]

  private var mToolbar: Toolbar = null
  private var mDrawer: DrawerLayout = null

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): CoordinatorLayout = {
    super.onCreate(savedState)
    val view = inflater.inflate(TR.layout.activity_profile, container, false)
    getActivity.getWindow.setStatusBarColor(Color.parseColor("#2b000000"))

    view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

    initToolbar(view)

    mDrawer = getActivity.findView(TR.home_layout)

    val mShareIDButton = view.findView(TR.profile_share_id)
    mShareIDButton.onClick {
      val dial = new SimpleShareDialogDesign(getActivity)
      dial.show()
    }

    val mChangeNickname = view.findView(TR.profile_change_nickname)
    mChangeNickname.onClick {
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

    val mChangeStatusMessage = view.findView(TR.profile_change_status_text)
    mChangeStatusMessage.onClick {
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

    val mChangeStatus = view.findView(TR.profile_change_status)
    mChangeStatus.onClick {
      val dial = new SimpleStatusDialogDesign(getActivity, 0)
      dial.show()
    }

    val mChangeReject = view.findView(TR.profile_change_reject_call)
    mChangeReject.onClick {
      startActivity(SIntent[RejectedCallMessages])
    }

    view
  }

  def initToolbar(view: View): Unit = {
    mToolbar = view.findView(TR.profile_toolbar)
    activity.setSupportActionBar(mToolbar)
    activity.getSupportActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_menu)
    activity.getSupportActionBar.setDisplayHomeAsUpEnabled(true)

    mToolbar.onClick {
      mDrawer.openDrawer(GravityCompat.START)
    }
  }

}
