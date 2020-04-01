package com.timehop.stickyheadersrecyclerview.calculation;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;

import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.MarginLayoutParams;

/**
 * Helper to calculate various view dimensions
 */
public class DimensionCalculator {

    /**
     * Populates {@link Rect} with margins for any view.
     *
     * @param margins rect to populate
     * @param view    for which to get margins
     */
    public void initMargins(@NonNull Rect margins, @NonNull View view) {
        LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof MarginLayoutParams) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
            initMarginRect(margins, marginLayoutParams);
        } else {
            margins.set(0, 0, 0, 0);
        }
    }

    /**
     * Converts {@link MarginLayoutParams} into a representative {@link Rect}.
     *
     * @param marginRect         Rect to be initialized with margins coordinates, where
     *                           {@link MarginLayoutParams#leftMargin} is equivalent to {@link Rect#left}, etc.
     * @param marginLayoutParams margins to populate the Rect with
     */
    private void initMarginRect(@NonNull Rect marginRect, @NonNull MarginLayoutParams marginLayoutParams) {
        marginRect.set(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
    }

}
