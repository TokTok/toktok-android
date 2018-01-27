package im.tox.toktok.app.domain;

public final class FriendMessage implements ChatMessage {
    public final Friend friend;
    public final String lastMessage;

    public FriendMessage(
            Friend friend,
            String lastMessage
    ) {
        this.friend = friend;
        this.lastMessage = lastMessage;
    }

    @Override
    public int messageType() {
        return 0;
    }
}
