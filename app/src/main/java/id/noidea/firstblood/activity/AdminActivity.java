package id.noidea.firstblood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.adapter.AdminAdapter;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.model.Posting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    private static String TAG = AdminActivity.class.getSimpleName();
    private List<Posting> postingList = new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private ApiData<List<Posting>> listPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

        Toolbar toolbars = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbars);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar!=null) {
            mActionBar.setTitle(null);
        }

        DbPosting dbPosting = new DbPosting(getApplicationContext());
        dbPosting.open();

        postingList.addAll(dbPosting.getAllPosting());

        RecyclerView recyclerView = findViewById(R.id.rv_admin);
        recyclerView.setAdapter(new AdminAdapter(getApplicationContext(), postingList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                ed = sp.edit();
                ed.clear();
                ed.apply();
                finish();
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
