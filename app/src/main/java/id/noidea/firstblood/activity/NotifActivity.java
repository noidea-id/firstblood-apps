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
    private List<Posting> postings_list = new ArrayList<>();
    //private static final String TAG = NotifActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        DbPosting dbP = new DbPosting(this);
        dbP.open();
        SharedPreferences sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

        DbUsers dbU = new DbUsers(this);
        dbU.open();
        String username = sp.getString("username", null);
        Users u = dbU.getUser(username);
        postings_list.addAll(dbP.getAllPostingByGoldar(u.getGoldar(), u.getRhesus()));


        RecyclerView rc_notif = findViewById(R.id.rc_notif);
        TimelineAdapter adapter = new TimelineAdapter(this, postings_list);
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

}
