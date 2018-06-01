package id.noidea.firstblood.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import id.noidea.firstblood.R;
import id.noidea.firstblood.adapter.TimelineAdapter;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.model.Posting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private static final String TAG = TimelineFragment.class.getSimpleName();
    private List<Posting> postings_list = new ArrayList<>();
    private Activity activity;
    private TimelineAdapter adapter;
    private boolean shouldStopLoop = false;
    private ApiData<List<Posting>> listPost;
    private SharedPreferences sp;
    private DbPosting dbP;
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
        dbP.open();

        postings_list.addAll(dbP.getAllPosting());

        RecyclerView rc_timeline = view.findViewById(R.id.rc_timeline);
        adapter = new TimelineAdapter(activity, postings_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rc_timeline.setLayoutManager(layoutManager);
        rc_timeline.setAdapter(adapter);
        sp = activity.getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);
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



    /*
     * Current Implementation of near Realtime timeline
     * call the backend every 10 second, and add whatever available there to db and refresh rcview
     * this operation is stopped at shouldStopLoop attribute
     * this is dirty solution, because network call is expensive
     * TODO think better solution that not require expensive operation
     */
    private void syncTimeline(){
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //do operation here
                getTimeline();
                if (!shouldStopLoop) {
                    mHandler.postDelayed(this, 10000);
                }else{
                    mHandler.removeCallbacks(this);
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
                        //to make sure no syn update when the runnable stopped
                        if(!shouldStopLoop){
                            setCurrentSync();
                            for (Posting pst : listPost.getData()) {
                                Integer check = posting_map.get(pst.getId_post());
                                if (check==null){//if value is not exist
                                    //add to list
                                    dbP.insertPosting(pst);
                                    postings_list.add(pst);
                                    //map the value
                                    posting_map.put(pst.getId_post(), postings_list.size()-1);
                                    adapter.notifyDataSetChanged();
                                }else{
                                    //update db
                                    dbP.updatePosting(pst);
                                    //get index
                                    //update list
                                    postings_list.set(check, pst);
                                    adapter.notifyItemChanged(check);
                                }
                            }
                            //TODO auto scroll only when user is at the top of timeline
                            //rc_timeline.getLayoutManager().scrollToPosition(postings_list.size() - 1);
                            //TODO remove toast when timeline is done
                            Toast.makeText(activity, "Synced", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(activity, "connection error", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData<List<Posting>>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(activity, "connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setCurrentSync(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        SharedPreferences.Editor ed;
        ed = sp.edit();
        ed.putString("last_sync", strDate);
        ed.apply();
    }
}


