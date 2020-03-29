package im.tox.toktok.app;

import android.content.res.Resources;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

public final class CompatUtil {

    public static int getColor(@NonNull Resources resources, @ColorRes int color) {
        int statusBarColor;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            statusBarColor = resources.getColor(color, null);
        } else {
            statusBarColor = resources.getColor(color);
        }
        return statusBarColor;
    }

}
