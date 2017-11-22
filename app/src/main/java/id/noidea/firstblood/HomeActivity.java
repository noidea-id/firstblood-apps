package id.noidea.firstblood;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import id.noidea.firstblood.Fragment.HomeFragment;
import id.noidea.firstblood.Fragment.ProfileFragment;
import id.noidea.firstblood.Fragment.TimelineFragment;

public class HomeActivity extends AppCompatActivity {

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

        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.setTextVisibility(false);
        navigation.setIconsMarginTop(32);
        navigation.setIconSize(26,26);
        navigation.setItemHeight(144);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Bundle extras = getIntent().getExtras();
        int position=0;
        if(extras != null) {
            position = extras.getInt("viewpager_position");
        }
        if(position==0){
            HomeFragment historyFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, historyFragment)
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
