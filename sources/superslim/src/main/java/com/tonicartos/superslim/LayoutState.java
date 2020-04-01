package com.tonicartos.superslim;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * State to track the current top mMarkerLine views are being mMarkerLine relative to.
 */
public class LayoutState {

    public final RecyclerView.Recycler recycler;

    private final RecyclerView.State recyclerState;

    @NonNull
    public final SparseArray<android.view.View> viewCache;

    public final boolean isLTR;

    public LayoutState(@NonNull RecyclerView.LayoutManager layoutManager, RecyclerView.Recycler recycler,
                       RecyclerView.State recyclerState) {
        viewCache = new SparseArray<>(layoutManager.getChildCount());
        this.recyclerState = recyclerState;
        this.recycler = recycler;
        isLTR = layoutManager.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_LTR;
    }

    public void cacheView(int position, android.view.View view) {
        viewCache.put(position, view);
    }

    public void decacheView(int position) {
        viewCache.remove(position);
    }

    public android.view.View getCachedView(int position) {
        return viewCache.get(position);
    }

    public RecyclerView.State getRecyclerState() {
        return recyclerState;
    }

    @NonNull
    public View getView(int position) {
        android.view.View child = getCachedView(position);
        boolean wasCached = child != null;
        if (child == null) {
            child = recycler.getViewForPosition(position);
        }

        return new View(child, wasCached);
    }

    public void recycleCache() {
        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }
    }

    public static class View {

        public final android.view.View view;

        public final boolean wasCached;

        public View(android.view.View child, boolean wasCached) {
            this.view = child;
            this.wasCached = wasCached;
        }

        @NonNull
        public LayoutManager.LayoutParams getLayoutParams() {
            return (LayoutManager.LayoutParams) view.getLayoutParams();
        }
    }
}
