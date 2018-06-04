package id.noidea.firstblood.activity;

import android.content.SharedPreferences;
import android.os.Handler;
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
import id.noidea.firstblood.model.Posting;
import id.noidea.firstblood.model.Users;

public class NotifActivity extends AppCompatActivity {
    private List<Posting> postings_list = new ArrayList<>();
    private boolean shouldStopLoop =false;
    private DbPosting dbP;
    private TimelineAdapter adapter;
    private SharedPreferences sp;
    DbUsers dbU;
    //private static final String TAG = NotifActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        dbP = new DbPosting(this);
        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

        dbU = new DbUsers(this);
        dbP.open();
        dbU.open();
        String username = sp.getString("username", null);
        Users u = dbU.getUser(username);
        postings_list.addAll(dbP.getAllPostingByGoldar(u.getGoldar(), u.getRhesus()));


        RecyclerView rc_notif = findViewById(R.id.rc_notif);
        adapter = new TimelineAdapter(this, postings_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rc_notif.setLayoutManager(layoutManager);
        rc_notif.setAdapter(adapter);

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

    private void syncTimeline(){
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //do operation here
                updateUI();
                if (!shouldStopLoop) {
                    mHandler.postDelayed(this, 10000);
                }else{
                    mHandler.removeCallbacks(this);
                    dbP.close();
                    dbU.close();
                }
            }
        };
        mHandler.post(runnable);
    }

    //only sync when opening this fragment
    // need to see fragment lifecycle
    @Override
    public void onStart() {
        super.onStart();
        syncTimeline();
        shouldStopLoop = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shouldStopLoop = true;
        dbP.close();
        dbU.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldStopLoop = true;
    }

    private void updateUI(){
        postings_list.clear();

        dbP.open();
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

}
