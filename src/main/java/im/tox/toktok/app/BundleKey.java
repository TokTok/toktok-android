package im.tox.toktok.app;

import android.support.annotation.NonNull;

import im.tox.toktok.app.TypedBundleKey.IntKey;
import im.tox.toktok.app.TypedBundleKey.StringKey;

public class BundleKey {
    public static final IntKey colorPrimary = new IntKey("colorPrimary");

    @NonNull
    public static final IntKey colorPrimary() {
        return colorPrimary;
    }

    public static final IntKey colorPrimaryStatus = new IntKey("colorPrimaryStatus");

    @NonNull
    public static final IntKey colorPrimaryStatus() {
        return colorPrimaryStatus;
    }

    public static final IntKey contactColorPrimary = new IntKey("contactColorPrimary");

    @NonNull
    public static final IntKey contactColorPrimary() {
        return contactColorPrimary;
    }

    public static final IntKey contactColorStatus = new IntKey("contactColorStatus");

    @NonNull
    public static final IntKey contactColorStatus() {
        return contactColorStatus;
    }

    public static final StringKey contactName = new StringKey("contactName");

    @NonNull
    public static final StringKey contactName() {
        return contactName;
    }

    public static final IntKey contactPhotoReference = new IntKey("contactPhotoReference");

    @NonNull
    public static final IntKey contactPhotoReference() {
        return contactPhotoReference;
    }

    public static final IntKey imgResource = new IntKey("imgResource");

    @NonNull
    public static final IntKey imgResource() {
        return imgResource;
    }

    public static final StringKey messageTitle = new StringKey("messageTitle");

    @NonNull
    public static final StringKey messageTitle() {
        return messageTitle;
    }

    public static final IntKey messageType = new IntKey("messageType");

    @NonNull
    public static final IntKey messageType() {
        return messageType;
    }
}
