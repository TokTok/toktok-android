package im.tox.toktok.app.message_activity;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.tox.toktok.R;
import im.tox.toktok.app.domain.Message;

final class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private static final Logger logger = LoggerFactory.getLogger(MessageAdapter.class);

    private final MessageClick messageClick;
    private final MessageActionMode messageActionMode;
    private final List<Message> messages = new ArrayList<>();
    private final SparseBooleanArray selectedItems = new SparseBooleanArray();
    private boolean actionModeActive = false;

    MessageAdapter(
            MessageClick messageClick,
            MessageActionMode messageActionMode
    ) {
        this.messageClick = messageClick;
        this.messageActionMode = messageActionMode;
    }

    @NonNull
    private static RelativeLayout inflate(int layout, @NonNull ViewGroup viewGroup) {
        return (RelativeLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(layout, viewGroup, false);
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).msgType.viewType;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RelativeLayout itemView;
        switch (viewType) {
            case 1: //MessageType.Delivered:
                itemView = inflate(R.layout.message_item_user_simple, viewGroup);
                return new MessageViewHolderDetailed(itemView, messageActionMode);
            case 2: //MessageType.Received.viewType =>
                itemView = inflate(R.layout.message_item_friend_simple, viewGroup);
                return new MessageViewHolderDetailed(itemView, messageActionMode);
            case 3: //MessageType.Action.viewType =>
                itemView = inflate(R.layout.message_item_action, viewGroup);
                return new MessageViewHolder(itemView, messageActionMode, itemView.findViewById(R.id.message_item_action));
            default:
                throw new RuntimeException("OMG");
        }
    }

    void toggleSelection(int i) {
        if (selectedItems.get(i, false)) {
            selectedItems.delete(i);
        } else {
            selectedItems.put(i, true);
        }
        notifyItemChanged(i);
    }

    int getSelectedItemCount() {
        return selectedItems.size();
    }

    private boolean isSelected(int position) {
        return selectedItems.get(position, false);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder viewHolder, int position) {
        Message message = messages.get(position);

        viewHolder.mUserText.setText(message.msgContent);
        viewHolder.mUserImg.setImageResource(message.imageSrc);

        if (actionModeActive && !isSelected(position)) {
            viewHolder.base.setAlpha(0.5f);
        } else {
            viewHolder.base.setAlpha(1);
        }
        MessageViewHolderDetailed view;
        switch (getItemViewType(position)) {
            case 1://MessageType.Delivered.viewType =>
                view = (MessageViewHolderDetailed) viewHolder;
                view.mUserDetails.setText(message.msgDetails);

            case 2://MessageType.Received.viewType =>
                viewHolder.mUserImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageClick.onImgClick();
                    }
                });

                view = (MessageViewHolderDetailed) viewHolder;
                view.mUserDetails.setText(message.msgDetails);

            case 3://MessageType.Action.viewType =>
                viewHolder.mUserImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageClick.onImgClick();
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    void addItem(Message newMsg) {
        messages.add(0, newMsg);
        notifyItemInserted(0);
    }

    void setActionModeActive(boolean state) {
        actionModeActive = state;
        logger.debug(String.valueOf(actionModeActive));
    }

    void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    void deleteSelected() {
        for (int a = messages.size() - 1; a >= 0; a--) {
            if (selectedItems.get(a)) {
                messages.remove(a);
                notifyItemRemoved(a);
            }
        }

        selectedItems.clear();
    }

}

class MessageViewHolder extends RecyclerView.ViewHolder
        implements View.OnLongClickListener
        , View.OnClickListener {

    private final MessageActionMode messageActionMode;

    final TextView mUserText;
    final CircleImageView mUserImg;
    final View base;

    MessageViewHolder(
            @NonNull RelativeLayout itemView,
            MessageActionMode messageActionMode,
            View base
    ) {
        super(itemView);
        mUserText = itemView.findViewById(R.id.message_item_text);
        mUserImg = itemView.findViewById(R.id.message_item_img);
        this.messageActionMode = messageActionMode;
        this.base = base;
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        return messageActionMode.onLongClick(getLayoutPosition());
    }

    @Override
    public void onClick(View v) {
        messageActionMode.onClick(getLayoutPosition());
    }

}

final class MessageViewHolderDetailed extends MessageViewHolder {

    final TextView mUserDetails;

    MessageViewHolderDetailed(
            @NonNull RelativeLayout itemView,
            MessageActionMode messageActionMode
    ) {
        super(
                itemView,
                messageActionMode,
                itemView.findViewById(R.id.message_item_base)
        );
        mUserDetails = itemView.findViewById(R.id.message_item_details);
    }
}

interface MessageClick {
    void onImgClick();
}

interface MessageActionMode {
    boolean onLongClick(int i);

    void onClick(int i);
}
