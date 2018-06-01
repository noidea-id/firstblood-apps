package id.noidea.firstblood.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.adapter.TimelineAdapter;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.fragment.TimelineFragment;
import id.noidea.firstblood.model.Posting;
import id.noidea.firstblood.model.Users;

public class NotifActivity extends AppCompatActivity {
    private RecyclerView rc_notif;
    private TimelineAdapter adapter;
    private List<Posting> postings_list = new ArrayList<>();
    private static final String TAG = NotifActivity.class.getSimpleName();
    private DbPosting dbP;
    private SharedPreferences sp;
    private DbUsers dbU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        dbP = new DbPosting(this);
        dbP.open();
        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

        dbU = new DbUsers(this);
        dbU.open();
        String username = sp.getString("username", null);
        Users u = dbU.getUser(username);
        postings_list.addAll(dbP.getAllPostingByGoldar(u.getGoldar(), u.getRhesus()));


        RecyclerView rc_timeline = findViewById(R.id.rc_notif);
        adapter = new TimelineAdapter(this, postings_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rc_timeline.setLayoutManager(layoutManager);
        rc_timeline.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
