package im.tox.toktok.app

class Message(msgType: Int, msgContent: String, msgDetails: String, imageSrc: Int) {

  def getType(): Int = {
    return msgType
  }

  def getMsgContent(): String = {
    return msgContent
  }

  def getMsgDetails(): String = {
    return msgDetails
  }

  def getImageSrc(): Int = {
    return imageSrc
  }

}

