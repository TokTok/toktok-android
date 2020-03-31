package im.tox.toktok.app.domain;

public class Message {
    public final MessageType msgType;
    public final String msgContent;
    public final String msgDetails;
    public final int imageSrc;

    public Message(
            MessageType msgType,
            String msgContent,
            String msgDetails,
            int imageSrc
    ) {
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.msgDetails = msgDetails;
        this.imageSrc = imageSrc;
    }
}
