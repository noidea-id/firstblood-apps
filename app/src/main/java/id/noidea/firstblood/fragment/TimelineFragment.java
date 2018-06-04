package id.noidea.firstblood.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.adapter.TimelineAdapter;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.model.Posting;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private static final String TAG = TimelineFragment.class.getSimpleName();
    private List<Posting> postings_list = new ArrayList<>();
    private Activity activity;
    private TimelineAdapter adapter;
    private DbPosting dbP;
    private boolean shouldStopLoop = false;
    private View view;
    //Map <key, value>
    //the map contain <id_posting, index_of_list>
    //map used to prevent iterate trough list
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> posting_map = new HashMap<>();


    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_timeline, container, false);
        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        ActionBar mActionBar = ((AppCompatActivity)activity).getSupportActionBar();
        if (mActionBar!=null) {
            mActionBar.setTitle(null);
        }
        dbP = new DbPosting(activity);
        this.view = view;
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_timeline, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity=(Activity) context;
        }
    }

    private void syncTimeline(){
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //do operation here
                updateUI();
                if (!shouldStopLoop) {
                    mHandler.postDelayed(this, 10000);
                }else{
                    mHandler.removeCallbacks(this);
                    dbP.close();
                }
            }
        };
        mHandler.post(runnable);
    }

    //only sync when opening this fragment
    // need to see fragment lifecycle
    @Override
    public void onStart() {
        super.onStart();
        syncTimeline();
        shouldStopLoop = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shouldStopLoop = true;
        dbP.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldStopLoop = true;
    }

    private void updateUI(){
        postings_list.clear();
        dbP.open();
        postings_list.addAll(dbP.getAllPosting());

        RecyclerView rc_timeline = view.findViewById(R.id.rc_timeline);
        adapter = new TimelineAdapter(activity, postings_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rc_timeline.setLayoutManager(layoutManager);
        rc_timeline.setAdapter(adapter);
    }
}


