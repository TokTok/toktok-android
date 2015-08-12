package im.tox.toktok.app.SimpleDialogs

import android.app.{Activity, Dialog}
import android.graphics.Color
import android.graphics.drawable.{ColorDrawable, TransitionDrawable}
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.{View, Window}
import android.widget.{Button, LinearLayout}
import im.tox.toktok.R


class SimpleStatusDialogDesign(activity: Activity, statusActive: Int) extends Dialog(activity, R.style.DialogSlideAnimation) {

  var a: Activity = activity
  var activeBackgroundTransition: TransitionDrawable = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_status_chooser_dialog_design)
    getWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))

    statusActive match {
      case 0 => {
        activeBackgroundTransition = findViewById(R.id.simple_dialog_status_online).asInstanceOf[LinearLayout].getBackground.asInstanceOf[TransitionDrawable]
      }
      case 1 => {
        activeBackgroundTransition = findViewById(R.id.simple_dialog_status_away).asInstanceOf[LinearLayout].getBackground.asInstanceOf[TransitionDrawable]
      }
      case 2 => {
        activeBackgroundTransition = findViewById(R.id.simple_dialog_status_busy).asInstanceOf[LinearLayout].getBackground.asInstanceOf[TransitionDrawable]
      }
    }

    activeBackgroundTransition.startTransition(250)

    val onlineStatus = findViewById(R.id.simple_dialog_status_online).asInstanceOf[LinearLayout]

    onlineStatus.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        activeBackgroundTransition.reverseTransition(250)
        activeBackgroundTransition = onlineStatus.getBackground.asInstanceOf[TransitionDrawable]
        activeBackgroundTransition.startTransition(250)
      }
    })

    val awayStatus = findViewById(R.id.simple_dialog_status_away).asInstanceOf[LinearLayout]

    awayStatus.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        activeBackgroundTransition.reverseTransition(250)
        activeBackgroundTransition = awayStatus.getBackground.asInstanceOf[TransitionDrawable]
        activeBackgroundTransition.startTransition(250)
      }
    })

    val busyStatus = findViewById(R.id.simple_dialog_status_busy).asInstanceOf[LinearLayout]

    busyStatus.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        activeBackgroundTransition.reverseTransition(250)
        activeBackgroundTransition = busyStatus.getBackground.asInstanceOf[TransitionDrawable]
        activeBackgroundTransition.startTransition(250)

      }
    })

    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[Button]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })

  }

}