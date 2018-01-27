package im.tox.toktok.app.domain;

import android.graphics.Color;

import im.tox.toktok.R;

public final class Friend {
    public static final Friend lorem = new Friend(1, "Lorem Ipsum", "Trying to TokTok", 0, Color.parseColor("#E91E63"), Color.parseColor("#C2185B"), R.drawable.lorem);
    public static final Friend john = new Friend(2, "John Doe", "Up!", 0, Color.parseColor("#3F51B5"), Color.parseColor("#303F9F"), R.drawable.john);
    public static final Friend jane = new Friend(3, "Jane Norman", "New Photo!", 0, Color.parseColor("#CDDC39"), Color.parseColor("#AFB42B"), R.drawable.jane);
    public static final Friend bart = new Friend(4, "Bart Simpson", "In vacation \uD83D\uDEA2", 0, Color.parseColor("#FF9800"), Color.parseColor("#F57C00"), R.drawable.bart);

    public final int id;
    public final String userName;
    public final String userMessage;
    public final int userStatus;
    public final int color;
    public final int secondColor;
    public final int photoReference;

    public Friend(
            int id,
            String userName,
            String userMessage,
            int userStatus,
            int color,
            int secondColor,
            int photoReference
    ) {
        this.id = id;
        this.userName = userName;
        this.userMessage = userMessage;
        this.userStatus = userStatus;
        this.color = color;
        this.secondColor = secondColor;
        this.photoReference = photoReference;
    }
}
