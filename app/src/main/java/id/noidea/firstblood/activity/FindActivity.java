package id.noidea.firstblood.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.noidea.firstblood.R;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.model.Posting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindActivity extends AppCompatActivity {

    private Button find;
    private Spinner s_goldar, s_rhesus;
    private EditText et_desc, et_rumah_sakit;
    private static final String TAG = FindActivity.class.getSimpleName();
    private SharedPreferences sp;
    private Posting p = new Posting();
    private DbPosting dbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        dbP = new DbPosting(this);
        dbP.open();
        et_desc = findViewById(R.id.et_desc);
        et_rumah_sakit = findViewById(R.id.et_rumah_sakit);
        find = findViewById(R.id.find);
        String[] arraySpinnerBlood = new String[]{
                "A", "B", "O", "AB"
        };
        s_goldar = findViewById(R.id.sp_goldar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinnerBlood);
        s_goldar.setAdapter(adapter);

        String[] arraySpinnerRhesus = new String[]{
                "+", "-"
        };
        s_rhesus = findViewById(R.id.sp_rhesus);
        ArrayAdapter<String> adapterR = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinnerRhesus);
        s_rhesus.setAdapter(adapterR);

        Intent i = getIntent();
        String acts = i.getStringExtra("id.noidea.firstblood.edit.act");
        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

        if (acts != null){
            setTitle("Edit Post");
            int id = i.getIntExtra("id.noidea.firstblood.edit.id", -1);
            p = dbP.getPosting(id);

            s_goldar.setSelection(adapter.getPosition(p.getGoldar()));
            s_rhesus.setSelection(adapterR.getPosition(p.getRhesus()));
            et_desc.setText(p.getDescrip());
            et_rumah_sakit.setText(p.getRumah_sakit());

            find.setOnClickListener(view -> updatePosting());
        }else {
            find.setOnClickListener(view -> addPosting());
        }
    }

    private void addPosting(){
        p.setGoldar(s_goldar.getSelectedItem().toString());
        p.setRhesus(s_rhesus.getSelectedItem().toString());
        p.setDescrip(et_desc.getText().toString().trim());
        p.setRumah_sakit(et_rumah_sakit.getText().toString().trim());
        p.setUsername(sp.getString("username", null));
        p.setStatus("0");
        String time = getCurrentTime();
        p.setInserted_at(time);
        p.setUpdated_at(time);

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(p.getDescrip())) {
            Toast.makeText(this, "Tolong isi deskripsi singkat", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(p.getRumah_sakit())) {
            Toast.makeText(this, "Tolong isi rumah sakit", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progressbar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.show();
        final Context c = this;
        String key = sp.getString("api_key", null);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<Integer>> call = apiService.addPosting(key, p.getUsername(), p.getGoldar(), p.getRhesus(),
                p.getDescrip(), p.getRumah_sakit(), p.getStatus(),p.getInserted_at(), p.getUpdated_at());
        call.enqueue(new Callback<ApiData<Integer>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<Integer>> call, @NonNull Response<ApiData<Integer>> response) {
                ApiData<Integer> posting = response.body();
                dialog.dismiss();

                if (posting != null) {
                    if (posting.getStatus().equals("success")){

                        Toast.makeText(c, "Permintaan Berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
                    }
                }
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<Integer>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private String getCurrentTime(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }

    private void updatePosting(){
        p.setGoldar(s_goldar.getSelectedItem().toString());
        p.setRhesus(s_rhesus.getSelectedItem().toString());
        p.setDescrip(et_desc.getText().toString().trim());
        p.setRumah_sakit(et_rumah_sakit.getText().toString().trim());
        String time = getCurrentTime();
        p.setUpdated_at(time);

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(p.getDescrip())) {
            Toast.makeText(this, "Tolong isi deskripsi singkat", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(p.getRumah_sakit())) {
            Toast.makeText(this, "Tolong isi rumah sakit", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progressbar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.show();
        final Context c = this;
        String key = sp.getString("api_key", null);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<Integer>> call = apiService.updatePosting(p.getId_post()+"", key, p.getGoldar(), p.getRhesus(),
                p.getDescrip(), p.getRumah_sakit(), p.getStatus(), p.getUpdated_at());
        call.enqueue(new Callback<ApiData<Integer>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<Integer>> call, @NonNull Response<ApiData<Integer>> response) {
                ApiData<Integer> posting = response.body();
                dialog.dismiss();

                if (posting != null) {
                    if (posting.getStatus().equals("success")){
                        Toast.makeText(c, "Posting Berhasil diedit", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
                    }
                }
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<Integer>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbP.close();
    }
}
