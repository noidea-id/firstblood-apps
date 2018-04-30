package id.noidea.firstblood.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import id.noidea.firstblood.model.Posting;

public class DbPosting {
    private SQLiteDatabase db;
    private final DbOpenHelper dbOH;
    public DbPosting(Context ctx){
        dbOH = new DbOpenHelper(ctx);
    }

    public void open() {
        db = dbOH.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertPosting(Posting p) {
        ContentValues newV = new ContentValues();
        newV.put("username", p.getUsername());
        newV.put("nama", p.getNama());
        newV.put("foto_profil", p.getFoto_profil());
        newV.put("goldar", p.getGoldar());
        newV.put("rhesus", p.getRhesus());
        newV.put("descrip", p.getDescrip());
        newV.put("rumah_sakit",p.getRumah_sakit());
        newV.put("status", p.getStatus());
        newV.put("inserted_at", p.getInserted_at());
        newV.put("updated_at", p.getUpdated_at());

        return db.insert("posting", null, newV);
    }

    public Posting getPosting(String username) {
        Cursor cur = null;
        Posting p = new Posting();


        String[] cols = new String[]{"nama", "foto_profil", "goldar",
                "rhesus", "descrip", "rumah_sakit", "status", "inserted_at", "updated_at"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + username};

        cur = db.query("posting", cols, "username=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            p.setUsername(username);
            p.setNama(cur.getString(0));
            p.setFoto_profil(cur.getString(1));
            p.setGoldar(cur.getString(2));
            p.setRhesus(cur.getString(3));
            p.setDescrip(cur.getString(4));
            p.setRumah_sakit(cur.getString(5));
            p.setStatus(cur.getString(6));
            p.setInserted_at(cur.getString(7));
            p.setUpdated_at(cur.getString(8));
        }
        cur.close();
        return p;
    }

    public ArrayList<Posting> getAllPosting() {
        Cursor cur = null;
        ArrayList<Posting> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM POSTING", null);
        if (cur.moveToFirst()) {
            do {
                Posting p = new Posting();
                p.setUsername(cur.getString(0));
                p.setNama(cur.getString(1));
                p.setFoto_profil(cur.getString(2));
                p.setGoldar(cur.getString(3));
                p.setRhesus(cur.getString(4));
                p.setDescrip(cur.getString(5));
                p.setRumah_sakit(cur.getString(6));
                p.setStatus(cur.getString(7));
                p.setInserted_at(cur.getString(8));
                p.setUpdated_at(cur.getString(9));
                out.add(p);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }

    public void deleteAll() {
        db.delete("users", null, null);
    }
}
