package im.tox.toktok.app

import android.graphics.Color

import scala.collection.mutable.ListBuffer

class Group(name: String, list: ListBuffer[Friend], color: Int, statusColor: Int) {

  def getGroupName(): String = {
    return name
  }

  def getFriendsList(): ListBuffer[Friend] = {
    return list
  }

  def getPrimaryColor(): Int = {
    return color
  }

  def getStatusColor(): Int = {
    return statusColor
  }

}

object Group {
  val group = new Group(" \uD83C\uDF20 The Amazing Group", ListBuffer(Friend.lorem, Friend.john), Color.parseColor("#9B9B9B"), Color.parseColor("#5A5A5A"))
}