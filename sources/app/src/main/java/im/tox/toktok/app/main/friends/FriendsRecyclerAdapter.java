package im.tox.toktok.app.main.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.R;
import im.tox.toktok.app.domain.Friend;

abstract class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerViewHolder> {

    private final List<Friend> friends;
    private final FriendItemClicks expandOnClick;

    private int expandedItem = -1;

    FriendsRecyclerAdapter(
            List<Friend> friends,
            FriendItemClicks expandOnClick
    ) {
        this.friends = friends;
        this.expandOnClick = expandOnClick;
    }

    @NonNull
    @Override
    public FriendsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_home_friends_item, viewGroup, false);

        return new FriendsRecyclerViewHolder(itemView, () -> {
            ViewHolder holder = (ViewHolder) itemView.getTag();

            if (expandedItem == holder.getLayoutPosition()) {
                notifyItemChanged(expandedItem);
                expandedItem = -1;
            } else {
                if (expandedItem >= 0) {
                    notifyItemChanged(expandedItem);
                }

                expandedItem = holder.getLayoutPosition();
                notifyItemChanged(expandedItem);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsRecyclerViewHolder viewHolder, int position) {
        Friend item = friends.get(position);
        viewHolder.mUserName.setText(item.userName);
        viewHolder.mUserImage.setImageResource(item.photoReference);
        viewHolder.mUserImage.setClickable(true);
        viewHolder.mUserImage.setOnClickListener(v -> expandOnClick.startOverLayFriend(viewHolder.getAdapterPosition()));

        if (position == expandedItem) {
            viewHolder.mExpandedArea.setVisibility(View.VISIBLE);
            viewHolder.mBase.setElevation(10);
            viewHolder.mBase.setBackgroundResource(R.drawable.cardboard_ripple);

            viewHolder.mCallButton.setOnClickListener(v -> expandOnClick.startCall(viewHolder.getAdapterPosition()));

            viewHolder.mMessageButton.setOnClickListener(v -> expandOnClick.startMessage(viewHolder.getAdapterPosition()));
        } else {
            viewHolder.mExpandedArea.setVisibility(View.GONE);
            viewHolder.mBase.setElevation(0);
            viewHolder.mBase.setBackgroundResource(R.drawable.background_ripple);

            viewHolder.mCallButton.setOnClickListener(null);
            viewHolder.mMessageButton.setOnClickListener(null);
        }

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    String getItemPosition(int i) {
        return friends.get(i).userName;
    }

    public Friend getItem(int i) {
        return friends.get(i);
    }

}

final class FriendsRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final FriendItemExpand itemOnClick;
    final RelativeLayout mBase;
    final RelativeLayout mUserInfo;
    final TextView mUserName;
    final CircleImageView mUserImage;
    final LinearLayout mExpandedArea;
    final TextView mCallButton;
    final TextView mMessageButton;

    FriendsRecyclerViewHolder(
            @NonNull RelativeLayout itemView,
            FriendItemExpand itemOnClick
    ) {
        super(itemView);
        this.itemOnClick = itemOnClick;
        mBase = itemView.findViewById(R.id.home_friends_base);
        mUserInfo = itemView.findViewById(R.id.home_item_info);
        mUserName = itemView.findViewById(R.id.home_friends_name);
        mUserImage = itemView.findViewById(R.id.home_friends_img);
        mExpandedArea = itemView.findViewById(R.id.home_friends_expanded_area);
        mCallButton = itemView.findViewById(R.id.home_friends_call);
        mMessageButton = itemView.findViewById(R.id.home_friends_message);

        mBase.setTag(this);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        itemOnClick.onClick();
    }

}

