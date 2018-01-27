package im.tox.toktok.app.contacts;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.tox.toktok.R;

final class FileSendActivityViewHolder extends RecyclerView.ViewHolder {

    ImageView mIcon;
    TextView mFileName;
    TextView mFileDate;

    public FileSendActivityViewHolder(RelativeLayout itemView) {
        super(itemView);
        mIcon = itemView.findViewById(R.id.files_send_item_icon);
        mFileName = itemView.findViewById(R.id.files_send_item_name);
        mFileDate = itemView.findViewById(R.id.files_send_item_date);
    }
}
