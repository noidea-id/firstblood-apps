package id.noidea.firstblood.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import id.noidea.firstblood.R;
import id.noidea.firstblood.db.DbPosting;
import id.noidea.firstblood.model.Posting;

public class DetailDonorActivity extends AppCompatActivity {

    /*

   private int id_post;
    private String  username, nama, foto_profil, goldar, rhesus, descrip, rumah_sakit, status, inserted_at, updated_at;
     */
    private DbPosting dbP;
    ImageView im_foto_profil;
    private TextView tv_username, tv_nama,
            tv_goldar_rhesus, tv_descrip, tv_rumah_sakit,
            tv_status, tv_inserted_at, tv_updated_at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donor);
        dbP = new DbPosting(this);
        dbP.open();
        tv_username = findViewById(R.id.tv_username);
        tv_nama = findViewById(R.id.tv_nama);
        im_foto_profil = findViewById(R.id.im_foto_profil);
        tv_goldar_rhesus = findViewById(R.id.tv_goldar_rhesus);
        tv_descrip = findViewById(R.id.tv_descrip);
        tv_rumah_sakit = findViewById(R.id.tv_rumah_sakit);
        tv_status = findViewById(R.id.tv_status);
        tv_inserted_at = findViewById(R.id.tv_inserted_at);
        tv_updated_at = findViewById(R.id.tv_updated_at);
        int id = getIntent().getIntExtra("id,noidea.firstblood.posting", -1);
        if (id!=-1){
            Posting p = dbP.getPosting(id);
            tv_username.setText(p.getUsername());
            tv_nama.setText(p.getNama());
            tv_goldar_rhesus.setText(p.getGoldar()+p.getRhesus());
            tv_descrip.setText(p.getDescrip());
            tv_rumah_sakit.setText(p.getRumah_sakit());
            if (p.getStatus().equals("0")){
                tv_status.setText(getString(R.string.status_0));
            }else if(p.getStatus().equals("1")){
                tv_status.setText(getString(R.string.status_1));
            }
            tv_inserted_at.setText(p.getInserted_at());
            tv_updated_at.setText(p.getUpdated_at());
        }
    }
}
