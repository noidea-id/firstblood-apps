package id.noidea.firstblood.activity;

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

import id.noidea.firstblood.R;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText et_user, et_pass;
    private ApiData<Users> account;
    private String user, pass;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private DbUsers dbU;

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
        boolean logged = sp.getBoolean("logged", false);
        if (!api_key.equals("") && !username.equals("") && logged) {
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    public void daftar(View v){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    public void login(View v){
        user = et_user.getText().toString().trim();
        pass = et_pass.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        ed = sp.edit();
                        ed.putString("username", account.getData().getUsername());
                        ed.putString("api_key", account.getData().getApi_key());
                        ed.putBoolean("logged", true);
                        ed.apply();
                        dbU.deleteAll();
                        long check = dbU.insertUser(account.getData());
                        Toast.makeText(c, "Berhasil Masuk", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                    else{
                        Toast.makeText(c, "Username atau email dan password salah", Toast.LENGTH_LONG).show();
                        return;
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
}
