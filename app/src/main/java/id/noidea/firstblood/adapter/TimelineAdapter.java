package id.noidea.firstblood.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Arrays;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.activity.DetailDonorActivity;
import id.noidea.firstblood.model.Posting;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder>{
    private List<Posting> mData;
    private LayoutInflater mInflater;
    //private int position;

    public TimelineAdapter(Context context, List<Posting> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_timeline, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(@NonNull final TimelineAdapter.ViewHolder holder, final int position) {
        final Posting pstng = mData.get(position);

        String url_foto_profil = pstng.getFoto_profil();
//        holder.im_profile.setImageDrawable(mInflater.getContext().getResources().getDrawable(R.drawable.user));
        holder.tv_desc.setText(pstng.getDescrip());
        holder.tv_goldar.setText(String.format("%s%s", pstng.getGoldar(), pstng.getRhesus()));
        if(pstng.getStatus().equals("0")){
            holder.tv_status.setText(mInflater.getContext().getString(R.string.status_0));
        }else if (pstng.getStatus().equals("1")){
            holder.tv_status.setText(mInflater.getContext().getString(R.string.status_1));
        }else if (pstng.getStatus().equals("2")){
            holder.tv_status.setText(mInflater.getContext().getString(R.string.status_2));
        }
        //damn mysql date format, have no choice but flip it like a fools
        String date = pstng.getInserted_at().split(" ")[0];
        String[] date2 = date.split("-");
        date = TextUtils.join("-", Arrays.asList(date2[2], date2[1], date2[0]));
        holder.tv_date.setText(date);

        holder.link_donor.setOnClickListener(v -> Toast.makeText(mInflater.getContext(), "donor "+pstng.getNama(), Toast.LENGTH_SHORT).show());
        holder.link_share.setOnClickListener(v -> Toast.makeText(mInflater.getContext(), "share "+pstng.getNama(), Toast.LENGTH_SHORT).show());

        holder.item_layout.setOnClickListener(v -> {
            Intent i = new Intent(mInflater.getContext(), DetailDonorActivity.class);
            i.putExtra("id,noidea.firstblood.posting", pstng.getId_post());
            mInflater.getContext().startActivity(i);
            //Toast.makeText(mInflater.getContext(), "detail "+pstng.getNama(), Toast.LENGTH_SHORT).show();
        });

        Glide.with(mInflater.getContext()).asBitmap().apply(RequestOptions.circleCropTransform()).load(url_foto_profil).into(holder.im_profile);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout link_donor, link_share;
        public ImageView im_profile;
        public TextView tv_desc, tv_goldar, tv_status, tv_date;
        public LinearLayout item_layout;
        //public ConstraintLayout matkul;

        ViewHolder(View itemView) {
            super(itemView);
            item_layout = itemView.findViewById(R.id.item_layout);
            link_donor = itemView.findViewById(R.id.link_donor);
            link_share = itemView.findViewById(R.id.link_share);
            im_profile = itemView.findViewById(R.id.im_profile);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_goldar = itemView.findViewById(R.id.tv_goldar);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

    }

    // convenience method for getting data at click position
    /*
    public Posting getItem(int id) {
        return mData.get(id);
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void filterList(ArrayList<Posting> filterdNames) {
        //TODO filtering
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
    */
}

