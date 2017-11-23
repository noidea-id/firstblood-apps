package id.noidea.firstblood.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;

import id.noidea.firstblood.Adapter.TimelineAdapter;
import id.noidea.firstblood.Item.Timeline;
import id.noidea.firstblood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private ListView listView;
    ArrayList<Timeline> timelineArrayList;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_timeline, container, false);
        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        listView = (ListView) view.findViewById(R.id.timeline);
        timelineArrayList = new ArrayList<>();
        timelineArrayList.add(new Timeline("user.jpg","Dibutuhkan segera, golongan darah A+ saat ini juga untuk Sdr. Fathi Abdat, yang sakit kompilasi (jantung, paru-paru, ginjal, diabetes).","B-","+","Menunggu","link.com", "11/22/2017"));
        timelineArrayList.add(new Timeline("user.jpg","malam saya ibu dari muhammad mutsaqoful fikri penderita anemia aplastik membutuhkan darah golongan darah B yg sedang di rawat di rsup fatmawati jakarta , mohon bantuannya untuk segenap handaitaulan","A+","+","Menunggu","link.com", "11/22/2017"));
        timelineArrayList.add(new Timeline("user.jpg","URGENT : dibutuhkan golongan darah B+ sebanyak 4 kantong untuk ibu dari teman saya di RSUD Ahmad Yani Metro Lampung Tengah. untuk teman yang berada di lampung dan mau membantu bisa hubungi WA/sms ke +62 896-4648-5244. mohon bantu share jika berkenan. terimakasih.","AB+","+","Menunggu","link.com", "11/22/2017"));
        TimelineAdapter adapter = new TimelineAdapter(getContext(),timelineArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_timeline, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
