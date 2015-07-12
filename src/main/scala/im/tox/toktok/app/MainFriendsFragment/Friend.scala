package im.tox.toktok.app.MainFriendsFragment

class Friend(userName: String,userMessage : String, userStatus: Int, color : Int, secondColor : Int, photo : Int) {

  def getUserName(): String = {
    return userName
  }

  def getUserMessage():String = {
    return userMessage
  }

  def getUserStatus(): Int = {
    return userStatus
  }

  def getColor() : Int = {
    return color
  }

  def getSecondColor() : Int = {
    return secondColor
  }

  def getPhotoReference() : Int = {
    return photo
  }

}
