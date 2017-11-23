package id.noidea.firstblood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class SignupActivity extends AppCompatActivity {

    private String[] arraySpinnerBlood;
    private String[] arraySpinnerRhesus;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        phone = (EditText) findViewById(R.id.phoneNumber);

        this.arraySpinnerBlood = new String[] {
                "A", "B", "O", "AB"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerBlood);
        s.setAdapter(adapter);

        this.arraySpinnerRhesus= new String[] {
                "+", "-"
        };
        Spinner sR = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapterR = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerRhesus);
        sR.setAdapter(adapterR);
    }

    public void daftar(View v){
        Intent i = new Intent(this, VerifyActivity.class);
        startActivity(i);
    }
}
