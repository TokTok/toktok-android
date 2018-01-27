package im.tox.toktok.app.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.domain.File;

final class FileSendActivityAdapter extends RecyclerView.Adapter<FileSendActivityViewHolder> {

    private final List<File> files;

    FileSendActivityAdapter(List<File> files) {
        this.files = files;
    }

    @Override
    public FileSendActivityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RelativeLayout itemView = (RelativeLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.files_send_item, viewGroup, false);
        return new FileSendActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FileSendActivityViewHolder viewHolder, int position) {
        File item = files.get(position);
        viewHolder.mFileDate.setText(item.date());
        viewHolder.mFileName.setText(item.name());
        viewHolder.mIcon.setImageResource(R.drawable.files_movie);

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

}
