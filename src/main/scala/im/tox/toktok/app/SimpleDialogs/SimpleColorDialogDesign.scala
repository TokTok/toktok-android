package im.tox.toktok.app.SimpleDialogs

import android.app.{Dialog, Activity}
import android.os.Bundle
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{View, Window}
import android.widget.{RelativeLayout, TextView, ImageView}
import im.tox.toktok.R
import im.tox.toktok.app.materialColors

class SimpleColorDialogDesign(activity: Activity, title: String, icon: Int, color: Int, clickAction: OnClickListener) extends Dialog(activity) {

  var a: Activity = activity

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_color_dialog_design)
    findViewById(R.id.simple_dialog_img).asInstanceOf[ImageView].setBackgroundResource(icon)
    findViewById(R.id.simple_dialog_text).asInstanceOf[TextView].setText(title)

    val color_recycler: RecyclerView = findViewById(R.id.simple_color_dialog_recyclerview).asInstanceOf[RecyclerView]

    val colors: materialColors = new materialColors
    color_recycler.setAdapter(new SimpleColorDialogDesignAdapter(colors.getColors()))
    color_recycler.setLayoutManager(new LinearLayoutManager(activity))



    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[RelativeLayout]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })

  }

}