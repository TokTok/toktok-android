package im.tox.toktok.app.CallActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.fivehundredpx.android.blur.BlurringView
import im.tox.toktok.R


class CallActivity extends AppCompatActivity {

  var background: ImageView = null

  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_call)
    background = findViewById(R.id.call_background).asInstanceOf[ImageView]
    //val backgroundBlurred: BlurringView = findViewById(R.id.call_background_blur).asInstanceOf[BlurringView]
    //backgroundBlurred.setBlurredView(background)


  }

}