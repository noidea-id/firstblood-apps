package id.noidea.firstblood.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import id.noidea.firstblood.fragment.HomeFragment;
import id.noidea.firstblood.fragment.ProfileFragment;
import id.noidea.firstblood.fragment.TimelineFragment;
import id.noidea.firstblood.R;

public class HomeActivity extends AppCompatActivity {
    Button donate_button;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_timeline:
                    TimelineFragment timelineFragment = new TimelineFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, timelineFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, homeFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, profileFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, homeFragment).addToBackStack(null).commit();

        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        donate_button = (Button) findViewById(R.id.donate_button);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Bundle extras = getIntent().getExtras();
        int position=1;
        if(extras != null) {
            position = extras.getInt("viewpager_position");
        }
        if(position==0){
            TimelineFragment timelineFragment = new TimelineFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, timelineFragment)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(0).setChecked(true);
        }else if(position==1){
            HomeFragment homeFragment1 = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, homeFragment1)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(1).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
