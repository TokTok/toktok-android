package im.tox.toktok.app.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import im.tox.toktok.R;

public final class RejectedCallMessages extends AppCompatActivity implements DragStart {

    private ItemTouchHelper itemDrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_call);

        Toolbar mToolbar = this.findViewById(R.id.reject_call_toolbar);
        mToolbar.setTitle("Reject call messages");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back_white);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView mRecycler = this.findViewById(R.id.reject_recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        mRecycler.setLayoutManager(mLayoutManager);

        List<String> a = new ArrayList<>();

        a.add("Sorry I’m In Class, Call you later");
        a.add("I’m at a meeting, can’t talk");
        a.add("Sorry I’m In Class, Call you later");
        a.add("I’m at a meeting, can’t talk");
        a.add("Sorry I’m In Class, Call you later");
        a.add("I’m at a meeting, can’t talk");

        RejectedCallAdapter mAdapter = new RejectedCallAdapter(a, this);
        mRecycler.setAdapter(mAdapter);

        DragHelperCallback itemDragCallback = new DragHelperCallback(mAdapter);
        itemDrag = new ItemTouchHelper(itemDragCallback);

        itemDrag.attachToRecyclerView(mRecycler);
    }

    @Override
    public void onDragStart(ViewHolder viewHolder) {
        itemDrag.startDrag(viewHolder);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

}
