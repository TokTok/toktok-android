package im.tox.toktok.app.simple_dialogs

import android.graphics.Color
import android.support.v7.widget.{ CardView, RecyclerView }
import android.view.View.OnClickListener
import android.view.{ LayoutInflater, View, ViewGroup }
import com.typesafe.scalalogging.Logger
import im.tox.toktok.TR
import im.tox.toktok.TypedResource._
import org.slf4j.LoggerFactory

final class SimpleColorDialogDesignAdapter(
    items: Seq[String]
) extends RecyclerView.Adapter[SimpleColorDialogDesignViewHolder] {

  private val logger = Logger(LoggerFactory.getLogger(getClass))

  override def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SimpleColorDialogDesignViewHolder = {
    val itemView = LayoutInflater.from(viewGroup.getContext)
      .inflate(TR.layout.simple_color_dialog_item, viewGroup, false)

    itemView.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        logger.debug("adsd")
        v.setZ(1000.0f)
      }
    })

    new SimpleColorDialogDesignViewHolder(itemView)
  }

  override def onBindViewHolder(viewHolder: SimpleColorDialogDesignViewHolder, position: Int): Unit = {
    val item = items(position)
    logger.debug(item)
    viewHolder.mColor.setBackgroundColor(Color.parseColor(item))
  }

  override def getItemCount: Int = {
    items.length
  }

}

final class SimpleColorDialogDesignViewHolder(
    itemView: CardView
) extends RecyclerView.ViewHolder(
  itemView
) with OnClickListener {

  val logger = Logger(LoggerFactory.getLogger(getClass))

  val mColor: View = itemView.findView(TR.simple_color_dialog_item)

  override def onClick(v: View) = {
    logger.debug("adsd")
  }

}
