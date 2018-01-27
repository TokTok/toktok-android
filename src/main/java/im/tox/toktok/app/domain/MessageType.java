package im.tox.toktok.app.domain;

public enum MessageType {
    Delivered(1),
    Received(2),
    Action(3);

    public final int viewType;

    MessageType(int viewType) {
        this.viewType = viewType;
    }
}
