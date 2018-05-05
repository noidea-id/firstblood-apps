package id.noidea.firstblood.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import id.noidea.firstblood.R;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getSimpleName();

    private EditText et_user, et_email, et_pass, et_pass_conf, et_nama;
    private Spinner sp_goldar, sp_rhesus;
    private EditText et_no_hp;

    String username, email, password, pass_conf, nama, goldar, rhesus , no_hp;
    ApiData<Integer> account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_user = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_pass_conf = findViewById(R.id.et_pass_conf);
        et_nama = findViewById(R.id.et_nama);
        et_no_hp = findViewById(R.id.et_no_hp);
        String[] arraySpinnerBlood = new String[]{
                "A", "B", "O", "AB"
        };
        sp_goldar = findViewById(R.id.sp_goldar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinnerBlood);
        sp_goldar.setAdapter(adapter);

        String[] arraySpinnerRhesus = new String[]{
                "+", "-"
        };
        sp_rhesus = findViewById(R.id.sp_rhesus);
        ArrayAdapter<String> adapterR = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinnerRhesus);
        sp_rhesus.setAdapter(adapterR);
    }

    public void daftar(View v){
        username = et_user.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_pass.getText().toString().trim();
        pass_conf = et_pass_conf.getText().toString().trim();
        nama = et_nama.getText().toString().trim();
        goldar = sp_goldar.getSelectedItem().toString();
        rhesus = sp_rhesus.getSelectedItem().toString();
        no_hp = et_no_hp.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progressbar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Harap masukkan Username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Harap masukkan Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Harap masukkan Password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass_conf)) {
            Toast.makeText(this, "Harap masukkan kembali Password", Toast.LENGTH_LONG).show();
            return;
        }else if (pass_conf.length()<8){
            Toast.makeText(this, "Password memenuhi minimal 8 karakter", Toast.LENGTH_LONG).show();
        }
        if (!password.equals(pass_conf)){
            Toast.makeText(this, "Password tidak sama, periksa kembali", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(this, "Harap masukkan Nama", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(no_hp)) {
            Toast.makeText(this, "Harap masukkan No Handphone", Toast.LENGTH_LONG).show();
            return;
        }
        dialog.show();

        final Context c = this;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiData<Integer>> call =
                apiService.postAccount(username, email, password,
                        nama, goldar, rhesus, no_hp, null);
        call.enqueue(new Callback<ApiData<Integer>>() {
            @Override
            public void onResponse(@NonNull Call<ApiData<Integer>> call,
                                   @NonNull Response<ApiData<Integer>> response) {
                account = response.body();
                dialog.dismiss();

                if (account != null) {
                    if (account.getStatus().equals("success")){
                        Toast.makeText(c, "Registered", Toast.LENGTH_LONG).show();
                        finish();
                        //TODO do we need Verification? and how to do it anyway?
                        //startActivity(new Intent(getApplicationContext(), VerifyActivity.class));
                    }
                    else{
                        Toast.makeText(c, "connection Error", Toast.LENGTH_LONG).show();
                        //dialog.dismiss();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<Integer>> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
