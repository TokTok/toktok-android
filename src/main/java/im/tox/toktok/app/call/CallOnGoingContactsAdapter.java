package im.tox.toktok.app.call;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.R;
import im.tox.toktok.app.domain.Friend;

final class CallOnGoingContactsAdapter extends RecyclerView.Adapter<CallOnGoingContactsViewHolder> {

    private final List<Friend> friends;

    CallOnGoingContactsAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public CallOnGoingContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.call_ongoing_contact, viewGroup, false);
        return new CallOnGoingContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CallOnGoingContactsViewHolder viewHolder, int position) {
        Friend item = friends.get(position);
        viewHolder.mFriendName.setText(item.userName);
        viewHolder.mFriendImage.setImageResource(item.photoReference);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

}

final class CallOnGoingContactsViewHolder extends RecyclerView.ViewHolder {

    final CircleImageView mFriendImage;
    final TextView mFriendName;
    final TextView mFriendCallTime;

    CallOnGoingContactsViewHolder(@NonNull RelativeLayout itemView) {
        super(itemView);
        mFriendImage = itemView.findViewById(R.id.call_ongoing_contact_img);
        mFriendName = itemView.findViewById(R.id.call_ongoing_contact_name);
        mFriendCallTime = itemView.findViewById(R.id.call_ongoing_contact_call_time);
    }

}
