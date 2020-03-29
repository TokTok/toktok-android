package im.tox.toktok.app;

import androidx.recyclerview.widget.RecyclerView;

public abstract class MyRecyclerScroll extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 100;
    private static final int SHOW_THRESHOLD = 50;

    private int scrollDist = 0;
    private boolean isVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isVisible && scrollDist > MyRecyclerScroll.HIDE_THRESHOLD) {
            hide();
            scrollDist = 0;
            isVisible = false;
        } else if (!isVisible && scrollDist < -MyRecyclerScroll.SHOW_THRESHOLD) {
            show();
            scrollDist = 0;
            isVisible = true;
        }
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }
    }

    protected abstract void show();

    protected abstract void hide();

}
