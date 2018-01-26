package im.tox.toktok.app.domain

sealed trait ChatMessage

final case class FriendMessage(
    friend: Friend,
    lastMessage: String
) extends ChatMessage

final case class GroupMessage(
    group: Group,
    lastMessage: String
) extends ChatMessage

object ChatMessage {
  val loremMessage = FriendMessage(Friend.lorem, "Hello, how are you?")
  val johnMessage = FriendMessage(Friend.john, "Hey buddy, how's things?")
  val groupMessage = GroupMessage(Group.group, "Let's Go!")

  def messageType(message: ChatMessage): Int = {
    message match {
      case FriendMessage(_, _) => 0
      case GroupMessage(_, _)  => 1
    }
  }
}
