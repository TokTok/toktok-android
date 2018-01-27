package im.tox.toktok.app;

import im.tox.toktok.app.TypedBundleKey.IntKey;
import im.tox.toktok.app.TypedBundleKey.StringKey;

public class BundleKey {
    public static final IntKey colorPrimary = new IntKey("colorPrimary");

    public static final IntKey colorPrimary() {
        return colorPrimary;
    }

    public static final IntKey colorPrimaryStatus = new IntKey("colorPrimaryStatus");

    public static final IntKey colorPrimaryStatus() {
        return colorPrimaryStatus;
    }

    public static final IntKey contactColorPrimary = new IntKey("contactColorPrimary");

    public static final IntKey contactColorPrimary() {
        return contactColorPrimary;
    }

    public static final IntKey contactColorStatus = new IntKey("contactColorStatus");

    public static final IntKey contactColorStatus() {
        return contactColorStatus;
    }

    public static final StringKey contactName = new StringKey("contactName");

    public static final StringKey contactName() {
        return contactName;
    }

    public static final IntKey contactPhotoReference = new IntKey("contactPhotoReference");

    public static final IntKey contactPhotoReference() {
        return contactPhotoReference;
    }

    public static final IntKey imgResource = new IntKey("imgResource");

    public static final IntKey imgResource() {
        return imgResource;
    }

    public static final StringKey messageTitle = new StringKey("messageTitle");

    public static final StringKey messageTitle() {
        return messageTitle;
    }

    public static final IntKey messageType = new IntKey("messageType");

    public static final IntKey messageType() {
        return messageType;
    }
}
