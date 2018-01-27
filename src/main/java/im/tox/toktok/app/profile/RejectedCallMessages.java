package im.tox.toktok.app.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
