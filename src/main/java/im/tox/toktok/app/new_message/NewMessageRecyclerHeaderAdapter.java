package im.tox.toktok.app.new_message;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.domain.Friend;

final class NewMessageRecyclerHeaderAdapter extends NewMessageRecyclerAdapter
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    NewMessageRecyclerHeaderAdapter(List<Friend> friends, FriendAddOnClick clickListener) {
        super(friends, clickListener);
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).userName().charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_header, parent, false);
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView text = holder.itemView.findViewById(R.id.recyclerview_header_text);
        text.setText(String.valueOf(getItem(position).userName().charAt(0)));
    }

}
