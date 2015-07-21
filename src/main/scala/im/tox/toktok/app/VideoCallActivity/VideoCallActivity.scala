package im.tox.toktok.app.VideoCallActivity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import im.tox.toktok.R


class VideoCallActivity extends AppCompatActivity{


  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_video_call)

    getWindow.getDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    getWindow.setStatusBarColor(getResources.getColor(R.color.contactsTransparentBar))

    findViewById(R.id.call_ongoing_fab).asInstanceOf[FloatingActionButton].setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        finish()
      }
    })

  }

  override def onBackPressed(): Unit ={

  }

}
