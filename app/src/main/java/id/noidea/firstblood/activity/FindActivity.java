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
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.model.Posting;
import id.noidea.firstblood.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindActivity extends AppCompatActivity {

    private Button find;
    private Spinner s_goldar, s_rhesus;
    private EditText et_desc, et_rumah_sakit;
    private static final String TAG = FindActivity.class.getSimpleName();
    private SharedPreferences sp;
    DbUsers dbU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

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
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPosting();
            }
        });
        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

    }
    private void addPosting(){
        Posting p = new Posting();
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
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
}
