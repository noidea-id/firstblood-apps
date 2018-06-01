package id.noidea.firstblood.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import id.noidea.firstblood.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIMEOUT = 3000;
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIMEOUT);
    }
}
