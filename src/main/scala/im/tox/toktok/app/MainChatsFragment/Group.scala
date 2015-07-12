package im.tox.toktok.app.MainChatsFragment

import im.tox.toktok.app.MainFriendsFragment.Friend

import scala.collection.mutable.ListBuffer

class Group(name :String,list: ListBuffer[Friend], color : Int, statusColor : Int){
  
  def getGroupName(): String = {
    return name
  }
  
  def getFriendsList(): ListBuffer[Friend] = {
    return list
  }

  def getPrimaryColor() : Int = {
    return color
  }

  def getStatusColor() : Int = {
    return statusColor
  }

}