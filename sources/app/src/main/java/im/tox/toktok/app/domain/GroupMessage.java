package im.tox.toktok.app.domain;

public final class GroupMessage implements ChatMessage {
    public final Group group;
    public final String lastMessage;

    public GroupMessage(
            Group group,
            String lastMessage
    ) {
        this.group = group;
        this.lastMessage = lastMessage;
    }

    @Override
    public int messageType() {
        return 1;
    }
}
