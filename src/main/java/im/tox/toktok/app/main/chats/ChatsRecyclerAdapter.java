package im.tox.toktok.app.main.chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.domain.ChatMessage;
import im.tox.toktok.app.domain.Friend;
import im.tox.toktok.app.domain.FriendMessage;
import im.tox.toktok.app.domain.Group;
import im.tox.toktok.app.domain.GroupMessage;
import im.tox.toktok.app.message_activity.MessageActivity;

import static im.tox.toktok.app.TypedBundleKey.SBundle;

final class ChatsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final java.util.List<ChatMessage> chatMessages;
    private final ChatItemClick chatItemClick;

    ChatsRecyclerAdapter(
            java.util.List<ChatMessage> chatMessages,
            ChatItemClick chatItemClick
    ) {
        this.chatMessages = chatMessages;
        this.chatItemClick = chatItemClick;
    }

    @NonNull
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        CardView itemView;
        switch (viewType) {
            case 0:
                itemView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.fragment_home_chats_item_user, viewGroup, false
                );
                return new ChatsRecyclerViewHolderUser(itemView, chatMessages, chatItemClick);
            case 1:
                itemView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.fragment_home_chats_item_group, viewGroup, false
                );
                return new ChatsRecyclerViewHolderGroup(itemView, chatMessages, chatItemClick);
        }

        throw new RuntimeException("Bad view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ChatMessage message = chatMessages.get(position);

        if (message instanceof FriendMessage) {
            Friend friend = ((FriendMessage) message).friend;
            String lastMessage = ((FriendMessage) message).lastMessage;
            ChatsRecyclerViewHolderUser view = (ChatsRecyclerViewHolderUser) viewHolder;

            view.mUserName.setText(friend.userName);
            view.mUserStatus.setText(friend.userMessage);
            view.mLastMessage.setText(lastMessage);
            view.mUserImage.setImageResource(friend.photoReference);
            view.mColor.setBackgroundColor(friend.color);

            if (isSelected(position)) {
                view.mSelectedBackground.setVisibility(View.VISIBLE);
            } else {
                view.mSelectedBackground.setVisibility(View.INVISIBLE);
            }
        } else if (message instanceof GroupMessage) {
            Group group = ((GroupMessage) message).group;
            String lastMessage = ((GroupMessage) message).lastMessage;
            ChatsRecyclerViewHolderGroup view = (ChatsRecyclerViewHolderGroup) viewHolder;

            view.mUserName.setText(group.groupName);
            view.mLastMessage.setText(lastMessage);
            view.mColor.setBackgroundColor(group.primaryColor);

            if (isSelected(position)) {
                view.mSelectedBackground.setVisibility(View.VISIBLE);
            } else {
                view.mSelectedBackground.setVisibility(View.INVISIBLE);
            }
        } else {
            throw new RuntimeException("Unsupported message type: " + message.getClass().getSimpleName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).messageType();
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    void toggleSelection(int i) {
        if (selectedItems.get(i, false)) {
            selectedItems.delete(i);
        } else {
            selectedItems.put(i, true);
        }

        notifyItemChanged(i);
    }

    void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        return selectedItems.size();
    }

    private boolean isSelected(int position) {
        return selectedItems.get(position, false);
    }

    void deleteSelected() {
        for (int a = chatMessages.size() - 1; a >= 0; a--) {
            if (selectedItems.get(a)) {
                chatMessages.remove(a);
                notifyItemRemoved(a);
            }
        }

        selectedItems.clear();
    }

}

abstract class ChatsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    final List<ChatMessage> chatMessages;
    final ChatItemClick clickListener;

    final View mSelectedBackground;
    final TextView mUserName;
    final TextView mLastMessage;
    final View mColor;

    ChatsRecyclerViewHolder(
            @NonNull View itemView,
            List<ChatMessage> chatMessages,
            ChatItemClick clickListener
    ) {
        super(itemView);
        this.chatMessages = chatMessages;
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mSelectedBackground = itemView.findViewById(R.id.home_item_selected);
        mUserName = itemView.findViewById(R.id.home_item_name);
        mLastMessage = itemView.findViewById(R.id.home_item_last_message);
        mColor = itemView.findViewById(R.id.home_item_color);
    }

    protected Context context() {
        return itemView.getContext();
    }

    @Override
    public boolean onLongClick(View v) {
        return clickListener.onLongClick(getLayoutPosition());
    }

}

final class ChatsRecyclerViewHolderGroup extends ChatsRecyclerViewHolder {

    ChatsRecyclerViewHolderGroup(
            @NonNull CardView itemView,
            java.util.List<ChatMessage> chatMessages,
            ChatItemClick clickListener
    ) {
        super(itemView, chatMessages, clickListener);
    }

    @Override
    public void onClick(View view) {
        if (!clickListener.onClick(getLayoutPosition())) {
            ChatMessage message = chatMessages.get(getLayoutPosition());
            Bundle bundle;
            if (message instanceof GroupMessage) {
                Group group = ((GroupMessage) message).group;
                bundle = SBundle(
                        BundleKey.messageType().map(1),
                        BundleKey.contactColorPrimary().map(group.primaryColor),
                        BundleKey.contactColorStatus().map(group.statusColor),
                        BundleKey.messageTitle().map(group.groupName)
                );
            } else {
                throw new RuntimeException("Unsupported message type: " + message.getClass().getSimpleName());
            }

            context().startActivity(new Intent(context(), MessageActivity.class).putExtras(bundle));
        }
    }

}

final class ChatsRecyclerViewHolderUser extends ChatsRecyclerViewHolder {

    final TextView mUserStatus;
    final CircleImageView mUserImage;

    ChatsRecyclerViewHolderUser(
            @NonNull View itemView,
            java.util.List<ChatMessage> chatMessages,
            ChatItemClick clickListener
    ) {
        super(itemView, chatMessages, clickListener);
        mUserStatus = itemView.findViewById(R.id.home_item_status);
        mUserImage = itemView.findViewById(R.id.home_item_img);
    }

    @Override
    public void onClick(View view) {
        if (!clickListener.onClick(getLayoutPosition())) {
            Bundle bundle;
            ChatMessage message = chatMessages.get(getLayoutPosition());
            if (message instanceof FriendMessage) {
                Friend friend = ((FriendMessage) message).friend;
                bundle = SBundle(
                        BundleKey.messageType().map(0),
                        BundleKey.contactColorPrimary().map(friend.color),
                        BundleKey.contactColorStatus().map(friend.secondColor),
                        BundleKey.messageTitle().map(friend.userName),
                        BundleKey.imgResource().map(friend.photoReference)
                );
            } else {
                throw new RuntimeException("Unsupported message type: " + message.getClass().getSimpleName());
            }

            context().startActivity(new Intent(context(), MessageActivity.class).putExtras(bundle));
        }
    }

}

