package id.noidea.firstblood.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.noidea.firstblood.R;
import id.noidea.firstblood.setting.SettingsFragment;

public class SettingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SettingsFragment()).commit();
        }
        setTitle(getString(R.string.appbar_setting));
    }
}
