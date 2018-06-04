package id.noidea.firstblood.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.noidea.firstblood.R;
import id.noidea.firstblood.api.ApiClient;
import id.noidea.firstblood.api.ApiData;
import id.noidea.firstblood.api.ApiInterface;
import id.noidea.firstblood.model.Posting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {

    private static final String TAG = AdminAdapter.class.getSimpleName();
    private List<Posting> mData;
    private LayoutInflater mInflater;
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    public AdminAdapter(Context context, List<Posting> mData) {
        this.mData = mData;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        sp = context.getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btn_confirm;
        ImageView profile_admin;
        TextView tv_desc_admin, tv_goldar_admin, tv_status_admin, tv_date_admin, id_post, intstatus, goldarraw, rhesusraw;
        TextView rs;

        public MyViewHolder(View itemView) {
            super(itemView);
            rs = itemView.findViewById(R.id.rs);
            btn_confirm = itemView.findViewById(R.id.btn_confirm);
            profile_admin = itemView.findViewById(R.id.profile_admin);
            tv_desc_admin = itemView.findViewById(R.id.tv_desc_admin);
            tv_goldar_admin = itemView.findViewById(R.id.tv_goldar_admin);
            tv_status_admin = itemView.findViewById(R.id.tv_status_admin);
            tv_date_admin = itemView.findViewById(R.id.tv_date_admin);
            id_post = itemView.findViewById(R.id.id_post);
            intstatus = itemView.findViewById(R.id.intstatus);
            goldarraw = itemView.findViewById(R.id.goldarraw);
            rhesusraw = itemView.findViewById(R.id.rhesusraw);

            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Date dt = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            final String currentTime = simpleDateFormat.format(dt);

            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer ints = Integer.valueOf(intstatus.getText().toString());
                    ints+=1;
                    Call<ApiData<Integer>> call = apiService.updatePosting(
                        id_post.getText().toString(),
                        sp.getString("api_key",""),
                        goldarraw.getText().toString(),
                        rhesusraw.getText().toString(),
                        tv_desc_admin.getText().toString(),
                        rs.getText().toString(),
                        String.valueOf(ints),
                        currentTime);
                    call.enqueue(new Callback<ApiData<Integer>>() {
                        @Override
                        public void onResponse(Call<ApiData<Integer>> call, Response<ApiData<Integer>> response) {
                            Log.d(TAG, "onResponse: " + response.body());
                        }

                        @Override
                        public void onFailure(Call<ApiData<Integer>> call, Throwable t) {
                            Log.d(TAG, "onFailure: "+t);
                        }
                    });
                    Toast.makeText(context, "Statusnya : "+ tv_status_admin.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
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

        holder.rs.setText(posting.getRumah_sakit());
        holder.id_post.setText(String.valueOf(posting.getId_post()));
        holder.intstatus.setText(posting.getStatus());
        holder.goldarraw.setText(posting.getGoldar());
        holder.rhesusraw.setText(posting.getRhesus());
        holder.tv_desc_admin.setText(posting.getDescrip());
        holder.tv_goldar_admin.setText(String.format("%s%s", posting.getGoldar(), posting.getRhesus()));
        if(posting.getStatus().equals("0")){
            holder.tv_status_admin.setText(mInflater.getContext().getString(R.string.status_0));
        }else if (posting.getStatus().equals("1")){
            holder.tv_status_admin.setText(mInflater.getContext().getString(R.string.status_1));
        }else if (posting.getStatus().equals("2")){
            holder.tv_status_admin.setText(mInflater.getContext().getString(R.string.status_2));
        }
        //damn mysql date format, have no choice but flip it like a fools
        String date = posting.getInserted_at().split(" ")[0];
        String[] date2 = date.split("-");
        date = TextUtils.join("-", Arrays.asList(date2[2], date2[1], date2[0]));
        holder.tv_date_admin.setText(date);

        Glide.with(mInflater.getContext()).asBitmap().apply(RequestOptions.circleCropTransform()).load(url_foto_profil).into(holder.profile_admin);

        if (posting.getStatus().equals("2")){
            holder.btn_confirm.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
