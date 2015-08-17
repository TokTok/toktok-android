package im.tox.toktok.app.MessageActivity

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.{CardView, RecyclerView}
import android.support.v7.widget.RecyclerView.ViewHolder
import android.text.TextUtils
import android.util.{SparseBooleanArray, Log}
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{LinearLayout, TextView}
import com.tonicartos.superslim.{LayoutManager, LinearSLM, GridSLM}
import im.tox.toktok.R
import im.tox.toktok.app.Message

import scala.collection.mutable.ListBuffer


class MessageRecallRecyclerAdapter(context : Context,list: ListBuffer[Message],recallMessageListener: RecallMessageListener) extends RecyclerView.Adapter[ViewHolder] {

  private val items: ListBuffer[LineItem] = new ListBuffer[LineItem]
  private val selectedItems: SparseBooleanArray = new SparseBooleanArray()
  var lastHeader : String = ""
  var sectionManager: Int = -1
  var headerCount: Int = 0
  var sectionFirstPosition: Int = 0
  var i = 0

  for(item <- list){
    val header = item.getMsgDetails()

    if(!TextUtils.equals(lastHeader, header)){
      sectionManager = (sectionManager + 1) % 2
      sectionFirstPosition = i + headerCount
      lastHeader = header
      headerCount += 1
      items += new LineItem(header,1,sectionManager,sectionFirstPosition)
    }
    items += new LineItem(item,0,sectionManager,sectionFirstPosition)
    i+=1
  }


  def onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder = {

    var itemView: View = null
    var viewHolder : ViewHolder = null

    if (viewType == 1) {
      itemView = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.recall_header, viewGroup, false)
      viewHolder = new MessageRecallRecyclerViewHolderHeader(itemView)
    } else {
      itemView = LayoutInflater.from(viewGroup.getContext).inflate(R.layout.recall_item, viewGroup, false)
      viewHolder = new MessageRecallRecyclerViewHolder(itemView)
    }

    return viewHolder

  }

  def onBindViewHolder(viewHolder: ViewHolder, position: Int) = {

    val item: LineItem = items(position)

    var lp = GridSLM.LayoutParams.from(viewHolder.itemView.getLayoutParams)

    if(item.isItemHeader()==1){

      val itemMessage = item.getContent().asInstanceOf[String]

      val view = viewHolder.asInstanceOf[MessageRecallRecyclerViewHolderHeader]

      view.mMessageText.setText(itemMessage)
      lp.headerDisplay = LayoutManager.LayoutParams.HEADER_STICKY  | LayoutManager.LayoutParams.HEADER_INLINE
      lp.isHeader = true
      lp.headerEndMarginIsAuto = false
      lp.headerStartMarginIsAuto = false

    }
    else{

      val itemMessage = item.getContent().asInstanceOf[Message]

      val view = (viewHolder.asInstanceOf[MessageRecallRecyclerViewHolder])

      view.mMessageText.setText(itemMessage.getMsgContent())
      view.mMessageDetails.setText(itemMessage.getMsgDetails())
      view.mMessageBase.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          recallMessageListener.onClick(position)
        }
      })

      if(isSelected(position)){
        view.mMessageBase.setBackgroundColor(Color.parseColor("#E0E0E0"))

      }else{
        view.mMessageBase.setBackgroundResource(R.color.cardBoardBackground)
      }

    }

    lp.setSlm(LinearSLM.ID)
    lp.setFirstPosition(item.getSectionFrist())
    viewHolder.itemView.setLayoutParams(lp)


  }

  def getItemCount(): Int = {
    return items.length
  }

  def isSelected(position: Int): Boolean = {
    return selectedItems.get(position, false)
  }

  def getItemPosition(position: Int): Object = {
    return items(position)
  }

  def toggleSelection(i: Int): Unit = {

    if (selectedItems.get(i, false)) {
      selectedItems.delete(i)
    }
    else {
      selectedItems.put(i, true)
    }
    notifyItemChanged(i)

  }

  override def getItemViewType(position : Int): Int = {
    return items(position).isItemHeader()
  }

  def getSelectedCount(): Int = {
    return selectedItems.size()
  }

}


final class MessageRecallRecyclerViewHolder(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  var mMessageText = itemView.findViewById(R.id.message_item_text).asInstanceOf[TextView]
  var mMessageDetails = itemView.findViewById(R.id.message_item_details).asInstanceOf[TextView]
  var mMessageBase = itemView.findViewById(R.id.message_item_base).asInstanceOf[CardView]

}

final class MessageRecallRecyclerViewHolderHeader(itemView: View) extends RecyclerView.ViewHolder(itemView) {

  var mMessageText = itemView.findViewById(R.id.recall_header_text).asInstanceOf[TextView]
  var mMessageBase = itemView.findViewById(R.id.recall_header_base).asInstanceOf[CardView]

}

final class LineItem(content: Object, isHeader: Int, sectionManager: Int, sectionFirstPosition: Int){

  def isItemHeader(): Int ={
    return isHeader
  }

  def getContent(): Object = {
    return content
  }

  def getSectionFrist():Int ={
    return sectionFirstPosition
  }

}

trait RecallMessageListener{
  def onClick(position : Int)
}