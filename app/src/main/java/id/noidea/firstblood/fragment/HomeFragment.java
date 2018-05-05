package id.noidea.firstblood.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;

import id.noidea.firstblood.activity.FindActivity;
import id.noidea.firstblood.activity.NotifActivity;
import id.noidea.firstblood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mDemoSlider;
    Button donate_button,request_button;
    private Activity activity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        ActionBar mActionBar = ((AppCompatActivity)activity).getSupportActionBar();
        if (mActionBar!=null) {
            mActionBar.setTitle(null);
        }

        mDemoSlider = view.findViewById(R.id.slider);
        donate_button = view.findViewById(R.id.donate_button);
        request_button = view.findViewById(R.id.request_button);

        HashMap<String,Integer> file_maps = new HashMap<>();
        file_maps.put("1",R.drawable.fact2);
        file_maps.put("2",R.drawable.fact3);
        file_maps.put("3",R.drawable.fact4);

        for(String name : file_maps.keySet()){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            // initialize a SliderLayout
            sliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            mDemoSlider.addSlider(sliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setDuration(4000);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        mDemoSlider.addOnPageChangeListener(this);
        final BottomNavigationViewEx navigation = activity.findViewById(R.id.navigation);
        donate_button.setOnClickListener(view1 -> {
            //emulate click instead call unlimeted activity that minght troubling our glorius garbage collector
            View v = navigation.findViewById(R.id.navigation_timeline);
            v.performClick();
        });
        request_button.setOnClickListener(view12 -> {
            Intent intent = new Intent(getContext(),FindActivity.class);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.navigation_more:
                Intent intent = new Intent(getContext(), NotifActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity=(Activity) context;
        }
    }
}
