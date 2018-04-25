package id.noidea.firstblood.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.noidea.firstblood.item.Timeline;
import id.noidea.firstblood.R;

/**
 * Created by ziterz on 11/22/2017.
 */

public class TimelineAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Timeline> timelineArrayList;

    public TimelineAdapter(Context context, ArrayList<Timeline> timelineArrayList) {
        this.context = context;
        this.timelineArrayList = timelineArrayList;
    }

    @Override
    public int getCount() {
        return timelineArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return timelineArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder holder = new ViewHolder();
        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.card_timeline, null);
            holder.picture = v.findViewById(R.id.picture);
            holder.desc = v.findViewById(R.id.desc);
            holder.blood = v.findViewById(R.id.blood);
            holder.status = v.findViewById(R.id.status);
            holder.date = v.findViewById(R.id.date);

            Timeline timeline = timelineArrayList.get(i);

            holder.desc.setText(timeline.getDesc());
            holder.blood.setText(timeline.getBlood());
            holder.status.setText(timeline.getStatus());
            holder.date.setText(timeline.getDate());
            holder.picture.setImageDrawable(context.getResources().getDrawable(R.drawable.user));
        }

        return v;
    }
    static class ViewHolder {
        ImageView picture;
        TextView desc, blood,status,date;
    }
    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
