package id.noidea.firstblood.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.adapter.TimelineAdapter;
import id.noidea.firstblood.model.Posting;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private RecyclerView rc_timeline;
    List<Posting> postings_list = new ArrayList<>();
    private Activity activity;
    TimelineAdapter adapter;

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
        ((AppCompatActivity)activity).getSupportActionBar().setTitle(null);
        rc_timeline = view.findViewById(R.id.rc_timeline);

        postings_list.add(new Posting(1,"a", "a", "a", "B","+", "dibutuhkan bantuan segera","a", "menunngu","20 april 2018", "c"));
        postings_list.add(new Posting(2,"a", "b", "a", "A","-", "dibutuhkan bantuan segera dibutuhkan bantuan segera","a", "selesai","20 februari 2018", "c"));
        postings_list.add(new Posting(3, "a", "c", "a", "AB","+", "dibutuhkan bantuan segera dibutuhkan bantuan segera dibutuhkan bantuan segera","a", "selesai","30 januari 2018", "c"));

        adapter = new TimelineAdapter(activity, postings_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        rc_timeline.setLayoutManager(layoutManager);
        rc_timeline.setAdapter(adapter);

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
}
