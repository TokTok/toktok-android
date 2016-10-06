package im.tox.toktok.app.simple_dialogs

import android.app.{ Activity, Dialog }
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.Window
import im.tox.toktok.TypedResource._
import im.tox.toktok.{ R, TR }
import org.scaloid.common._

final class SimpleTextDialogDesign(
    activity: Activity,
    title: String,
    color: Int,
    icon: Int,
    value: String,
    clickAction: OnClickListener
) extends Dialog(activity, R.style.DialogSlideAnimation) {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_input_dialog_design)
    getWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))

    this.findView(TR.simple_dialog_color).setBackgroundTintList(ColorStateList.valueOf(color))
    this.findView(TR.simple_dialog_img).setImageResource(icon)
    this.findView(TR.simple_dialog_text).setText(title)

    val input = this.findView(TR.simple_dialog_input)
    input.setText(value)

    val cancelButton = this.findView(TR.simple_dialog_cancel)
    cancelButton.onClick {
      dismiss()
    }

    val confirmButton = this.findView(TR.simple_dialog_confirm)

    input.afterTextChanged {
      confirmButton.setEnabled(true)
      confirmButton.setTextColor(activity.getResources.getColor(R.color.simpleDialogTextButton, null))
      confirmButton.setBackgroundTintList(ColorStateList.valueOf(activity.getResources.getColor(R.color.simpleDialogIconButton, null)))
      confirmButton.setOnClickListener(clickAction)
    }
  }
}
