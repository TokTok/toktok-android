package im.tox.toktok.app.CallActivity

import android.graphics.{Color, Bitmap}
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import im.tox.toktok.R


class CallActivity extends AppCompatActivity {


  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_call_layout)

    getWindow.getDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    getWindow.setStatusBarColor(Color.parseColor("#2b000000"))


    val background = findViewById(R.id.call_background).asInstanceOf[ImageView]

    val content: View = findViewById(android.R.id.content).getRootView


    content.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener {
      override def onGlobalLayout(): Unit = {
        val picture = BlurBuilder.blur(background)
        background.setImageDrawable(new BitmapDrawable(getResources,picture))

      }
    })
  }
}