package im.tox.toktok.app.call;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import im.tox.toktok.R;

final class CallMessageAdapter extends RecyclerView.Adapter<CallMessageViewHolder> {

    private final List<String> excuses;

    CallMessageAdapter(List<String> excuses) {
        this.excuses = excuses;
    }

    @Override
    public CallMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LinearLayout itemView = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.call_item, viewGroup, false);
        return new CallMessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CallMessageViewHolder viewHolder, int position) {
        String item = excuses.get(position);
        viewHolder.mMessage.setText(item);
    }

    @Override
    public int getItemCount() {
        return excuses.size();
    }

}

final class CallMessageViewHolder extends RecyclerView.ViewHolder {

    final TextView mMessage;

    CallMessageViewHolder(@NonNull LinearLayout itemView) {
        super(itemView);
        this.mMessage = itemView.findViewById(R.id.call_item_message);
    }

}
