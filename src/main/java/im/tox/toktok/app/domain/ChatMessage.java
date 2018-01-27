package im.tox.toktok.app.domain;

public interface ChatMessage {
    int messageType();

    ChatMessage loremMessage = new FriendMessage(Friend.lorem, "Hello, how are you?");
    ChatMessage johnMessage = new FriendMessage(Friend.john, "Hey buddy, how's things?");
    ChatMessage groupMessage = new GroupMessage(Group.group, "Let's Go!");
}
