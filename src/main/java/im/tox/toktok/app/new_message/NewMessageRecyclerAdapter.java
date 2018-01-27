package im.tox.toktok.app.new_message;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.R;
import im.tox.toktok.app.domain.Friend;

abstract class NewMessageRecyclerAdapter extends RecyclerView.Adapter<NewMessageRecyclerViewHolder> implements Filterable {

    private static final Logger logger = LoggerFactory.getLogger(NewMessageRecyclerAdapter.class);

    private SparseBooleanArray selectedItems;
    private List<Friend> selectedContacts;
    private List<Friend> savedContacts;
    public List<Friend> friends;
    FriendAddOnClick listener;

    NewMessageRecyclerAdapter(
            @NonNull List<Friend> friends,
            FriendAddOnClick listener
    ) {
        this.savedContacts = new ArrayList<>(friends);
        this.friends = new ArrayList<>(friends);
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
        this.selectedContacts = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewMessageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_message_item, viewGroup, false);
        return new NewMessageRecyclerViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewMessageRecyclerViewHolder viewHolder, int position) {
        Friend item = friends.get(position);
        viewHolder.mUserName.setText(item.userName);
        viewHolder.mUserImage.setImageResource(item.photoReference);

        AlphaAnimation animation;
        if (selectedItems.get(item.id, false)) {
            animation = new AlphaAnimation(1, 0.3F);
            animation.setDuration(0);
            animation.setFillAfter(true);
        } else {
            animation = new AlphaAnimation(0.3F, 1);
            animation.setDuration(0);
            animation.setFillAfter(true);
        }

        viewHolder.mBase.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        logger.debug("getItemCount: " + friends.size());
        return friends.size();
    }

    Friend getItem(int i) {
        return friends.get(i);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new FriendFilter(this, savedContacts);
    }

    void selectItem(int position) {
        if (selectedItems.get(friends.get(position).id, false)) {
            selectedItems.delete(friends.get(position).id);
            selectedContacts.remove(friends.get(position));
        } else {
            selectedItems.put(friends.get(position).id, true);
            selectedContacts.add(friends.get(position));
        }

        notifyItemChanged(position);
    }

    int selectedCount() {
        return selectedItems.size();
    }

    Friend getFirstSelected() {
        return selectedContacts.get(0);
    }

    void clearSelectedList() {
        selectedItems.clear();
        selectedContacts.clear();

        notifyDataSetChanged();
    }

    List<Friend> getItems() {
        return friends;
    }

    List<Friend> getSelectedFriends() {
        return selectedContacts;
    }

}

final class NewMessageRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    final TextView mUserName;
    final CircleImageView mUserImage;
    final RelativeLayout mBase;
    private final FriendAddOnClick clickListener;


    NewMessageRecyclerViewHolder(
            @NonNull RelativeLayout itemView,
            FriendAddOnClick clickListener
    ) {
        super(itemView);
        mUserName = itemView.findViewById(R.id.new_message_item_name);
        mUserImage = itemView.findViewById(R.id.new_message_item_img);
        mBase = itemView.findViewById(R.id.new_message_item);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setTag(this);
    }

    @Override
    public void onClick(View v) {
        clickListener.onClickListener(((NewMessageRecyclerViewHolder) itemView.getTag()).getLayoutPosition());
    }

}

interface FriendAddOnClick {
    void onClickListener(int position);
}

final class FriendFilter extends Filter {

    private final NewMessageRecyclerAdapter adapter;
    private final List<Friend> friendsList;
    @NonNull
    private List<Friend> filteredResults = new ArrayList<Friend>();

    FriendFilter(
            NewMessageRecyclerAdapter adapter,
            List<Friend> friendsList
    ) {
        this.adapter = adapter;
        this.friendsList = friendsList;
    }

    @NonNull
    @Override
    protected FilterResults performFiltering(@NonNull CharSequence constraint) {
        filteredResults.clear();
        FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredResults = new ArrayList<>(friendsList);
        } else {
            String trimmedString = constraint.toString().toLowerCase().trim();

            for (Friend a : friendsList) {
                if (a.userName.toLowerCase().trim().contains(trimmedString)) {
                    filteredResults.add(a);
                }
            }
        }

        results.values = filteredResults;
        results.count = filteredResults.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, @NonNull FilterResults results) {
        adapter.friends.clear();
        adapter.friends = (List<Friend>) results.values;
        adapter.notifyDataSetChanged();
    }

}
