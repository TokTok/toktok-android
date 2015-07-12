package im.tox.toktok.app.SimpleDialogs

import android.app.{Activity, Dialog}
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.{View, Window}
import android.widget.{ImageView, RelativeLayout, TextView}
import im.tox.toktok.R

class SimpleDialogDesign(activity: Activity, question: String, icon: Int, clickAction: OnClickListener) extends Dialog(activity) {

  var a: Activity = activity

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_dialog_design)
    findViewById(R.id.simple_dialog_img).asInstanceOf[ImageView].setBackgroundResource(icon)
    findViewById(R.id.simple_dialog_text).asInstanceOf[TextView].setText(question)


    val confirmButton = findViewById(R.id.simple_dialog_confirm).asInstanceOf[RelativeLayout]
    confirmButton.setOnClickListener(clickAction)


    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[RelativeLayout]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })
  }
}