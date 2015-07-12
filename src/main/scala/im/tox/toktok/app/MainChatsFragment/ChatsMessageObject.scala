package im.tox.toktok.app.MainChatsFragment

import im.tox.toktok.app.MainFriendsFragment.Friend

class ChatsMessageObject() {

  var typeMessage: Int = 0
  var lastMessage: String = ""
  var user: Friend = null
  var group: Group = null
  var color: Int = 0
  var colorStatus: Int = 0

  def getUser(): Friend = {
    return user
  }

  def getType(): Int = {
    return typeMessage
  }

  def getGroup(): Group = {
    return group
  }

  def getLastMessage(): String = {
    return lastMessage
  }

  def getPrimaryColor(): Int = {
    return color
  }

  def getStatusColor(): Int = {
    return colorStatus
  }

  def this(group: Group, lastMessage: String) {
    this
    typeMessage = 1
    this.group = group
    this.lastMessage = lastMessage
    this.color = group.getPrimaryColor()
    this.colorStatus = group.getStatusColor()
  }

  def this(friend: Friend, lastMessage: String) {
    this
    typeMessage = 0
    this.user = friend
    this.lastMessage = lastMessage
    this.color = user.getColor()
    this.colorStatus = user.getSecondColor()
  }

}
