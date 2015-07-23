package im.tox.toktok.app.SimpleDialogs

import android.app.{Activity, Dialog}
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.{View, Window}
import android.widget.Button
import im.tox.toktok.R


class SimpleShareDialogDesign(activity: Activity) extends Dialog(activity) {

  var a: Activity = activity

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_share_dialog_design)
    getWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))


    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[Button]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })

  }

}