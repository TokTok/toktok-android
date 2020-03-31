package im.tox.toktok.app.domain;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public final class Group {
    public static final Group group = new Group(
            " \uD83C\uDF20 The Amazing Group",
            Arrays.asList(
                    Friend.lorem,
                    Friend.john
            ),
            Color.parseColor("#9B9B9B"),
            Color.parseColor("#5A5A5A")
    );

    public final String groupName;
    public final List<Friend> friendsList;
    public final int primaryColor;
    public final int statusColor;

    public Group(
            String groupName,
            List<Friend> friendsList,
            int primaryColor,
            int statusColor
    ) {
        this.groupName = groupName;
        this.friendsList = friendsList;
        this.primaryColor = primaryColor;
        this.statusColor = statusColor;
    }
}
