package im.tox.toktok.app.domain

sealed abstract class MessageType(val viewType: Int)
object MessageType {
  case object Delivered extends MessageType(1)
  case object Received extends MessageType(2)
  case object Action extends MessageType(3)
}

final case class Message(
  msgType: MessageType,
  msgContent: String,
  msgDetails: String,
  imageSrc: Int
)
