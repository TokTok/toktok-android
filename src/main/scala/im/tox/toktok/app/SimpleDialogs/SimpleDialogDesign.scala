package im.tox.toktok.app.SimpleDialogs

import android.app.{Activity, Dialog}
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.view.{View, Window}
import android.widget._
import im.tox.toktok.R

class SimpleDialogDesign(activity: Activity, question: String, color: Int, icon: Int, clickAction: OnClickListener) extends Dialog(activity, R.style.DialogSlideAnimation) {

  var a: Activity = activity
  var mBase : RelativeLayout = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_dialog_design)
    getWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))

    findViewById(R.id.simple_dialog_color).asInstanceOf[LinearLayout].setBackgroundTintList(ColorStateList.valueOf(color))
    findViewById(R.id.simple_dialog_img).asInstanceOf[ImageView].setImageResource(icon)

    findViewById(R.id.simple_dialog_text).asInstanceOf[TextView].setText(question)

    mBase = findViewById(R.id.simple_dialog_base).asInstanceOf[RelativeLayout]

    val confirmButton = findViewById(R.id.simple_dialog_confirm).asInstanceOf[Button]
    confirmButton.setOnClickListener(clickAction)


    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[Button]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })

  }

}