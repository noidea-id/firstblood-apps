package id.noidea.firstblood.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Arrays;
import java.util.List;

import id.noidea.firstblood.R;
import id.noidea.firstblood.model.Posting;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {

    private List<Posting> mData;
    private LayoutInflater mInflater;
    private Context context;

    public AdminAdapter(Context context, List<Posting> mData) {
        this.mData = mData;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btn_confirm;
        ImageView profile_admin;
        TextView tv_desc_admin, tv_goldar_admin, tv_status_admin, tv_date_admin;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_confirm = itemView.findViewById(R.id.btn_confirm);
            profile_admin = itemView.findViewById(R.id.profile_admin);
            tv_desc_admin = itemView.findViewById(R.id.tv_desc_admin);
            tv_goldar_admin = itemView.findViewById(R.id.tv_goldar_admin);
            tv_status_admin = itemView.findViewById(R.id.tv_status_admin);
            tv_date_admin = itemView.findViewById(R.id.tv_date_admin);

            btn_confirm.setOnClickListener(v -> Toast.makeText(context, "Clicked : "+tv_desc_admin.getText().toString(), Toast.LENGTH_SHORT).show());
        }
    }

    @NonNull
    @Override
    public AdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.admin_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Posting posting = mData.get(position);

        String url_foto_profil = posting.getFoto_profil();

        holder.tv_desc_admin.setText(posting.getDescrip());
        holder.tv_goldar_admin.setText(String.format("%s%s", posting.getGoldar(), posting.getRhesus()));
        if(posting.getStatus().equals("0")){
            holder.tv_status_admin.setText(mInflater.getContext().getString(R.string.status_0));
        }else if (posting.getStatus().equals("1")){
            holder.tv_status_admin.setText(mInflater.getContext().getString(R.string.status_1));
        }
        //damn mysql date format, have no choice but flip it like a fools
        String date = posting.getInserted_at().split(" ")[0];
        String[] date2 = date.split("-");
        date = TextUtils.join("-", Arrays.asList(date2[2], date2[1], date2[0]));
        holder.tv_date_admin.setText(date);

        Glide.with(mInflater.getContext()).asBitmap().apply(RequestOptions.circleCropTransform()).load(url_foto_profil).into(holder.profile_admin);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
