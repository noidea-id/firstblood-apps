package id.noidea.firstblood.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import id.noidea.firstblood.activity.LoginActivity;
import id.noidea.firstblood.R;
import id.noidea.firstblood.db.DbUsers;
import id.noidea.firstblood.model.Users;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }
    private TextView tv_nama, tv_email, tv_goldar, tv_rhesus, tv_no_hp;
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private Activity activity;
    private DbUsers dbU;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        sp = activity.getSharedPreferences("id.noidea.firstblood.user", Context.MODE_PRIVATE);
        dbU = new DbUsers(activity);
        dbU.open();

        tv_nama = view.findViewById(R.id.tv_nama);
        tv_email = view.findViewById(R.id.tv_email);
        tv_goldar = view.findViewById(R.id.tv_goldar);
        tv_rhesus = view.findViewById(R.id.tv_rhesus);
        tv_no_hp = view.findViewById(R.id.tv_no_hp);

        loadProfile();

        Button logout = view.findViewById(R.id.btnLogout);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        ActionBar mActionBar = ((AppCompatActivity)activity).getSupportActionBar();
        if (mActionBar!=null) {
            mActionBar.setTitle(null);
        }

        logout.setOnClickListener(view1 -> {
            Intent i = new Intent(getContext(), LoginActivity.class);
            ed = sp.edit();
            ed.clear();
            ed.apply();
            activity.finish();
            startActivity(i);
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity=(Activity) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbU.close();
    }

    public void loadProfile(){
        String username = sp.getString("username", "");
        Users us = dbU.getUser(username);
        tv_nama.setText(us.getNama());
        tv_email.setText(us.getEmail());
        tv_goldar.setText(us.getGoldar());
        tv_rhesus.setText(us.getRhesus());
        tv_no_hp.setText(us.getNo_hp());
    }
}
