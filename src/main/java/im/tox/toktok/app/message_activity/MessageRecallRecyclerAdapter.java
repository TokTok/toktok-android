package im.tox.toktok.app.message_activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LinearSLM;

import java.util.ArrayList;
import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.domain.Message;

final class MessageRecallRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final RecallMessageListener recallMessageListener;
    private List<LineItem> items = new ArrayList<LineItem>();
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public MessageRecallRecyclerAdapter(
            Context context,
            List<Message> messages,
            RecallMessageListener recallMessageListener
    ) {
        this.recallMessageListener = recallMessageListener;

        for (Message message : messages) {
            String header = message.msgDetails;

            String lastHeader = "";
            int sectionManager = -1;
            int sectionFirstPosition = 0;
            int i = 0;
            if (!TextUtils.equals(lastHeader, header)) {
                sectionManager = (sectionManager + 1) % 2;
                int headerCount = 0;
                sectionFirstPosition = i + headerCount;
                lastHeader = header;
                headerCount += 1;
                items.add(new LineItem(header, 1, sectionManager, sectionFirstPosition));
            }
            items.add(new LineItem(message, 0, sectionManager, sectionFirstPosition));
            i += 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == 1) {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recall_header, viewGroup, false);
            return new MessageRecallRecyclerViewHolderHeader(itemView);
        } else {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recall_item, viewGroup, false);
            return new MessageRecallRecyclerViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        LineItem item = items.get(position);

        GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(viewHolder.itemView.getLayoutParams());

        if (item.isItemHeader == 1) {

            CharSequence itemMessage = (String) item.content;

            MessageRecallRecyclerViewHolderHeader view = (MessageRecallRecyclerViewHolderHeader) viewHolder;

            view.mMessageText.setText(itemMessage);
            lp.isHeader = true;
            lp.headerEndMarginIsAuto = false;
            lp.headerStartMarginIsAuto = false;

        } else {

            Message itemMessage = (Message) item.content;

            MessageRecallRecyclerViewHolder view = (MessageRecallRecyclerViewHolder) viewHolder;

            view.mMessageText.setText(itemMessage.msgContent);
            view.mMessageDetails.setText(itemMessage.msgDetails);
            view.mMessageBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recallMessageListener.onClick(position);
                }
            });

            if (isSelected(position)) {
                view.mMessageBase.setBackgroundColor(Color.parseColor("#E0E0E0"));

            } else {
                view.mMessageBase.setBackgroundResource(R.color.cardBoardBackground);
            }

        }

        lp.setSlm(LinearSLM.ID);
        lp.setFirstPosition(item.sectionFirstPosition);
        viewHolder.itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private boolean isSelected(int position) {
        return selectedItems.get(position, false);
    }

    Object getItemPosition(int position) {
        return items.get(position);
    }

    void toggleSelection(int i) {
        if (selectedItems.get(i, false)) {
            selectedItems.delete(i);
        } else {
            selectedItems.put(i, true);
        }
        notifyItemChanged(i);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isItemHeader;
    }

    int getSelectedCount() {
        return selectedItems.size();
    }

}

final class MessageRecallRecyclerViewHolder extends RecyclerView.ViewHolder {
    MessageRecallRecyclerViewHolder(LinearLayout itemView) {
        super(itemView);
    }

    TextView mMessageText = itemView.findViewById(R.id.message_item_text);
    TextView mMessageDetails = itemView.findViewById(R.id.message_item_details);
    CardView mMessageBase = itemView.findViewById(R.id.message_item_base);
}

final class MessageRecallRecyclerViewHolderHeader extends RecyclerView.ViewHolder {
    MessageRecallRecyclerViewHolderHeader(LinearLayout itemView) {
        super(itemView);
    }

    TextView mMessageText = itemView.findViewById(R.id.recall_header_text);
    CardView mMessageBase = itemView.findViewById(R.id.recall_header_base);
}

final class LineItem {
    final Object content;
    final int isItemHeader;
    final int sectionManager;
    final int sectionFirstPosition;

    LineItem(
            Object content,
            int isItemHeader,
            int sectionManager,
            int sectionFirstPosition
    ) {
        this.content = content;
        this.isItemHeader = isItemHeader;
        this.sectionManager = sectionManager;
        this.sectionFirstPosition = sectionFirstPosition;
    }
}

interface RecallMessageListener {
    void onClick(int position);
}
