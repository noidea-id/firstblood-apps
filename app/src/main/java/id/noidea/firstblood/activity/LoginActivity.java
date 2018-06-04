package id.noidea.firstblood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.model.Posting;
import id.noidea.firstblood.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText et_user, et_pass;
    private ApiData<Users> account;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private DbUsers dbU;

    private List<Posting> postings_list = new ArrayList<>();
    private ApiData<List<Posting>> listPost;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> posting_map = new HashMap<>();
    private DbPosting dbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_user = findViewById(R.id.et_user);
        et_pass = findViewById(R.id.et_pass);
        dbU = new DbUsers(this);
        dbU.open();
        sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);

        String username = sp.getString("username", "");
        String api_key = sp.getString("api_key", "");
        String role = sp.getString("role","");
        boolean logged = sp.getBoolean("logged", false);
        if (!api_key.equals("") && !username.equals("") && logged && role.equals("user")) {
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }else if(!api_key.equals("") && !username.equals("") && logged && role.equals("rumahsakit")){
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        }
    }

    public void daftar(View v){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    public void login(View v){
        String user = et_user.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.progressbar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "Tolong isi username atau email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Tolong isi password", Toast.LENGTH_LONG).show();
            return;
        }
        dialog.show();
        final Context c = this;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<Users>> call = apiService.getUserLogin(user, pass);
        call.enqueue(new Callback<ApiData<Users>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<Users>> call, @NonNull Response<ApiData<Users>> response) {
                account = response.body();
                dialog.dismiss();

                if (account != null) {
                    if (account.getStatus().equals("success")){
                        getTimeline();
                        ed = sp.edit();
                        ed.putString("username", account.getData().getUsername());
                        ed.putString("api_key", account.getData().getApi_key());
                        ed.putBoolean("logged", true);
                        ed.putString("last_sync", "0000-00-00 00.00.00");
                        ed.putString("role", account.getData().getRole());
                        ed.apply();
                        dbU.deleteAll();
                        dbU.insertUser(account.getData());
                        Toast.makeText(c, "Berhasil Masuk", Toast.LENGTH_LONG).show();
                        finish();
                        if (account.getData().getRole().equals("user"))
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        else
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    }
                    else{
                        Toast.makeText(c, "Username atau email dan password salah", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<Users>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbU.close();
    }

    private void getTimeline(){
        listPost = new ApiData<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String key = sp.getString("api_key", null);
        String date = sp.getString("last_sync", "0000-00-00 00.00.00");

        Call<ApiData<List<Posting>>> call = apiService.getLatestPosting(key,date);
        call.enqueue(new Callback<ApiData<List<Posting>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<List<Posting>>> call, @NonNull Response<ApiData<List<Posting>>> response) {
                listPost = response.body();

                if (listPost != null) {
                    if (listPost.getStatus().equals("success")){
                        for (Posting pst : listPost.getData()) {
                            Integer check = posting_map.get(pst.getId_post());
                            if (check==null){//if value is not exist
                                //add to list
                                dbP.insertPosting(pst);
                                postings_list.add(pst);
                                //map the value
                                posting_map.put(pst.getId_post(), postings_list.size()-1);
                            }else{
                                //update db
                                dbP.updatePosting(pst);
                                //get index
                                //update list
                                postings_list.set(check, pst);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<Posting>>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

}
