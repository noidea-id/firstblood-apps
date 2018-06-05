package id.noidea.firstblood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import id.noidea.firstblood.R;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.model.Posting;

public class DetailDonorActivity extends AppCompatActivity {

    private ImageView im_foto_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donor);

        DbPosting dbP = new DbPosting(this);
        im_foto_profil = findViewById(R.id.im_foto_profil);
        TextView tv_username, tv_nama,
                tv_goldar_rhesus, tv_descrip, tv_rumah_sakit,
                tv_status, tv_inserted_at, tv_updated_at;
        Button bt_aksi1, bt_aksi2;


        dbP.open();
//        tv_username = findViewById(R.id.tv_username);
        tv_nama = findViewById(R.id.tv_nama);
        tv_goldar_rhesus = findViewById(R.id.tv_goldar_rhesus);
        tv_descrip = findViewById(R.id.tv_descrip);
        tv_rumah_sakit = findViewById(R.id.tv_rumah_sakit);
        tv_status = findViewById(R.id.tv_status);
//        tv_inserted_at = findViewById(R.id.tv_inserted_at);
        tv_updated_at = findViewById(R.id.tv_updated_at);

        bt_aksi1 = findViewById(R.id.bt_aksi1);
        bt_aksi2 = findViewById(R.id.bt_aksi2);
        bt_aksi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaitVerifActivity.class);
                startActivity(intent);
            }
        });

        int id = getIntent().getIntExtra("id,noidea.firstblood.posting", -1);
        if (id!=-1){
            final Posting p = dbP.getPosting(id);
//            tv_username.setText(p.getUsername());
            tv_nama.setText(p.getNama());
            tv_goldar_rhesus.setText(String.format("%s%s", p.getGoldar(), p.getRhesus()));
            tv_descrip.setText(p.getDescrip());
            tv_rumah_sakit.setText(p.getRumah_sakit());
            if (p.getStatus().equals("0")){
                tv_status.setText(getString(R.string.status_0));
            }else if(p.getStatus().equals("1")){
                tv_status.setText(getString(R.string.status_1));
            }
//            tv_inserted_at.setText(p.getInserted_at());
            tv_updated_at.setText(p.getUpdated_at());

            Glide.with(getApplicationContext()).asBitmap().apply(RequestOptions.circleCropTransform()).load(p.getFoto_profil()).into(im_foto_profil);

            SharedPreferences sp = getSharedPreferences("id.noidea.firstblood.user", MODE_PRIVATE);
            String username = sp.getString("username", "");
            if (username.equals(p.getUsername())){
                bt_aksi1.setText(getString(R.string.aksi_edit));
                bt_aksi1.setOnClickListener(v -> {
                    Intent i = new Intent(getBaseContext(), FindActivity.class);
                    i.putExtra("id.noidea.firstblood.edit.act", "EditPost");
                    i.putExtra("id.noidea.firstblood.edit.id", p.getId_post());
                    startActivity(i);
                });

                bt_aksi2.setText(getString(R.string.aksi_tutup));
            }else{
                bt_aksi1.setText(getString(R.string.aksi_detail_user));
                bt_aksi2.setText(getString(R.string.aksi_donor));
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
