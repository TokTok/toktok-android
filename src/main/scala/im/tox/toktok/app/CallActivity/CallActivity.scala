package im.tox.toktok.app.CallActivity

import android.graphics.{Point, Color}
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.{Display, View}
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.{RelativeLayout, LinearLayout, SeekBar, ImageView}
import im.tox.toktok.R

import scala.collection.mutable.ListBuffer


class CallActivity extends AppCompatActivity {


  protected override def onCreate(savedInstanceState: Bundle): Unit = {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_call_layout)

    getWindow.getDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    getWindow.setStatusBarColor(Color.parseColor("#2b000000"))

    val background = findViewById(R.id.call_background).asInstanceOf[ImageView]
    val screen : Display = getWindowManager().getDefaultDisplay()
    val screenSize : Point = new Point()
    screen.getSize(screenSize)


    val content: View = findViewById(android.R.id.content).getRootView

    content.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener {
      override def onGlobalLayout(): Unit = {
        val picture = BlurBuilder.blur(background)
        background.setImageDrawable(new BitmapDrawable(getResources,picture))

      }
    })


    val minHeight : Double = screenSize.y*0.5

    val bottomPainel : LinearLayout = findViewById(R.id.call_bottom_painel).asInstanceOf[LinearLayout]
    val params : RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, minHeight.toInt)
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

    bottomPainel.setLayoutParams(params)

    findViewById(R.id.call_top_painel).asInstanceOf[RelativeLayout].setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, minHeight.toInt));


    val mRecycler : RecyclerView = findViewById(R.id.call_messages_recycler).asInstanceOf[RecyclerView]
    val list: ListBuffer[String] = new ListBuffer[String]
    list += "Call me Later"

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    mRecycler.setAdapter(new CallAdapter(list))

    val declineSeekbar : SeekBar = findViewById(R.id.call_decline).asInstanceOf[SeekBar]
    declineSeekbar.setRotation(180);




  }
}