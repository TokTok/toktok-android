package im.tox.toktok.app.contacts;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import im.tox.toktok.R;
import im.tox.toktok.app.BundleKey;
import im.tox.toktok.app.domain.File;

public final class FileSendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_send);

        Bundle bundle = getIntent().getExtras();

        int colorPrimary = BundleKey.contactColorPrimary.get(bundle);
        int colorStatus = BundleKey.contactColorStatus.get(bundle);
        String userName = BundleKey.contactName.get(bundle);

        getWindow().setStatusBarColor(colorStatus);

        Toolbar mToolbar = this.findViewById(R.id.files_send_toolbar);
        mToolbar.setBackgroundColor(colorPrimary);
        mToolbar.setTitle(getResources().getString(R.string.files_send_title) + " " + userName);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView mRecycler = this.findViewById(R.id.files_send_recycler);
        List<File> list = Collections.singletonList(File.file);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecycler.setLayoutManager(mLayoutManager);

        mRecycler.setAdapter(new FileSendActivityAdapter(list));
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
