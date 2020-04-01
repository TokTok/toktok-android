package im.tox.toktok.app.main.friends;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.domain.Friend;

public final class FriendsRecyclerHeaderAdapter
        extends FriendsRecyclerAdapter
        implements StickyRecyclerHeadersAdapter<FriendsRecyclerHeaderViewHolder> {

    public FriendsRecyclerHeaderAdapter(
            List<Friend> friends,
            FriendItemClicks friendPhotoOnClick
    ) {
        super(friends, friendPhotoOnClick);
    }

    @NonNull
    private CharSequence getHeaderText(int position) {
        return String.valueOf(getItemPosition(position).charAt(0));
    }

    @Override
    public long getHeaderId(int position) {
        return getItemPosition(position).charAt(0);
    }

    @NonNull
    @Override
    public FriendsRecyclerHeaderViewHolder onCreateHeaderViewHolder(@NonNull ViewGroup parent) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_header, parent, false);
        return new FriendsRecyclerHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(@NonNull FriendsRecyclerHeaderViewHolder holder, int position) {
        holder.headerText.setText(getHeaderText(position));
    }

}

final class FriendsRecyclerHeaderViewHolder extends RecyclerView.ViewHolder {
    final TextView headerText;

    FriendsRecyclerHeaderViewHolder(@NonNull LinearLayout itemView) {
        super(itemView);
        headerText = itemView.findViewById(R.id.recycler_view_header_text);
    }
}
