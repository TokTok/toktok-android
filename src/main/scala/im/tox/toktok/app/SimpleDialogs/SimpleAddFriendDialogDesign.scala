package im.tox.toktok.app.SimpleDialogs

import android.app.{Activity, Dialog}
import android.os.Bundle
import android.text.{Editable, TextWatcher}
import android.view.View.OnClickListener
import android.view.{View, Window}
import android.widget.{EditText, ImageView, RelativeLayout, TextView}
import im.tox.toktok.R


class SimpleAddFriendDialogDesign(activity: Activity, clickAction: OnClickListener) extends Dialog(activity) {

  var a: Activity = activity

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_addfriend_dialog_design)
    findViewById(R.id.simple_dialog_img).asInstanceOf[ImageView].setBackgroundResource(R.drawable.ic_person_black_48dp)
    findViewById(R.id.simple_dialog_text).asInstanceOf[TextView].setText("Adding a new friend")

    val input: EditText = findViewById(R.id.simple_dialog_input).asInstanceOf[EditText]

    val confirmButton = findViewById(R.id.simple_dialog_confirm).asInstanceOf[RelativeLayout]


    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[RelativeLayout]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })

    input.addTextChangedListener(new TextWatcher {

      override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int): Unit = {}

      override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int): Unit = {}

      override def afterTextChanged(s: Editable): Unit = {

        confirmButton.setAlpha(1)
        confirmButton.setOnClickListener(clickAction)

      }
    })


  }
}