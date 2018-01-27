package im.tox.toktok.app.message_activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tonicartos.superslim.LayoutManager;

import java.util.ArrayList;
import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.domain.Message;
import im.tox.toktok.app.domain.MessageType;

final class MessageRecallActivity extends AppCompatActivity implements RecallMessageListener {

    @Nullable
    private MessageRecallRecyclerAdapter adapter = null;
    @Nullable
    private FloatingActionButton mFAB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recall_message);

        Bundle bundle = getIntent().getExtras();

        int colorPrimary = BundleKey.colorPrimary().get(bundle);
        int colorStatus = BundleKey.colorPrimaryStatus().get(bundle);

        Toolbar mToolbar = this.findViewById(R.id.recall_toolbar);
        mToolbar.setBackgroundColor(colorPrimary);
        getWindow().setStatusBarColor(colorStatus);

        mFAB = this.findViewById(R.id.recall_fab);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView mRecycler = this.findViewById(R.id.recall_recycler);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(
                MessageType.Delivered,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "23th of April",
                R.drawable.lorem
        ));
        for (int i = 0; i < 10; i++) {
            messages.add(new Message(
                    MessageType.Delivered,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "24th of April",
                    R.drawable.lorem
            ));
        }

        mRecycler.setLayoutManager(new LayoutManager(this));

        adapter = new MessageRecallRecyclerAdapter(this, messages, this);

        mRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        adapter.toggleSelection(position);

        final int selectedCount = adapter.getSelectedCount();

        if (selectedCount == 0) {
            getSupportActionBar().setTitle("Recall Messages");
            mFAB.hide();
        } else if (selectedCount == 1) {
            getSupportActionBar().setTitle(selectedCount + " selected message");
            mFAB.show();
        } else {
            getSupportActionBar().setTitle(selectedCount + " selected messages");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
