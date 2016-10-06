package im.tox.toktok.app.simple_dialogs

import android.app.{ Activity, Dialog }
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import im.tox.toktok.TypedResource._
import im.tox.toktok.{ R, TR }
import org.scaloid.common._

final class SimpleShareDialogDesign(activity: Activity) extends Dialog(activity, R.style.DialogSlideAnimation) {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_share_dialog_design)
    getWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))

    val cancelButton = this.findView(TR.simple_dialog_cancel)
    cancelButton.onClick {
      dismiss()
    }
  }

}
