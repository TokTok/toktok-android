package im.tox.toktok.app.message_activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import im.tox.toktok.R;

public final class MessageAttachments extends Fragment {

    @Override
    public LinearLayout onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (LinearLayout) inflater.inflate(R.layout.overlay_attachments, container, false);
    }

}
