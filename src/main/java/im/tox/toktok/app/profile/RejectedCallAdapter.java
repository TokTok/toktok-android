package im.tox.toktok.app.profile;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import im.tox.toktok.R;

final class RejectedCallAdapter
        extends RecyclerView.Adapter<RejectedCallViewHolder>
        implements DragInterface {

    private final List<String> items;
    private final DragStart dragStart;

    RejectedCallAdapter(List<String> items, DragStart dragStart) {
        this.items = items;
        this.dragStart = dragStart;
    }

    @Override
    public RejectedCallViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rejected_call_item, viewGroup, false);
        return new RejectedCallViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RejectedCallViewHolder viewHolder, int position) {
        String item = items.get(position);
        viewHolder.mMessage.setText(item);

        viewHolder.itemView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dragStart.onDragStart(viewHolder);
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemMove(int originalPosition, int newPosition) {
        String originalItem = items.get(originalPosition);
        items.set(originalPosition, items.get(newPosition));
        items.set(newPosition, originalItem);
        notifyItemMoved(originalPosition, newPosition);
    }

}

final class RejectedCallViewHolder extends RecyclerView.ViewHolder {
    final TextView mMessage;

    RejectedCallViewHolder(RelativeLayout itemView) {
        super(itemView);
        mMessage = itemView.findViewById(R.id.reject_item_message);
    }
}

final class DragHelperCallback extends ItemTouchHelper.Callback {

    private final RejectedCallAdapter adapter;

    DragHelperCallback(RejectedCallAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return Callback.makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction) {
    }

}

interface DragInterface {
    void onItemMove(int originalPosition, int newPosition);
}

interface DragStart {
    void onDragStart(ViewHolder viewHolder);
}
