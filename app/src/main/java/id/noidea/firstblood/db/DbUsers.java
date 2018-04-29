package id.noidea.firstblood.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import id.noidea.firstblood.model.Users;

public class DbUsers {
    private SQLiteDatabase db;
    private final DbOpenHelper dbOH;
    public DbUsers(Context ctx){
        dbOH = new DbOpenHelper(ctx);
    }

    public void open() {
        db = dbOH.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertUser(Users u) {
        ContentValues newV = new ContentValues();
        //newV.put("kode_mk", k.getKode_mk());
        newV.put("username", u.getUsername());
        newV.put("email", u.getEmail());
        newV.put("nama", u.getNama());
        newV.put("goldar", u.getGoldar());
        newV.put("rhesus", u.getRhesus());
        newV.put("no_hp", u.getNo_hp());
        newV.put("foto_profil",u.getFoto_profil());

        return db.insert("users", null, newV);
    }

    public Users getUser(String username) {
        Cursor cur = null;
        Users u = new Users();


        String[] cols = new String[]{"email, nama, goldar, rhesus, no_hp, foto_profil"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + username};

        cur = db.query("users", cols, "username=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            u.setUsername(username);
            u.setEmail(cur.getString(0));
            u.setNama(cur.getString(1));
            u.setGoldar(cur.getString(2));
            u.setRhesus(cur.getString(3));
            u.setNo_hp(cur.getString(4));
            u.setFoto_profil(cur.getString(5));
        }
        cur.close();
        return u;
    }

    public ArrayList<Users> getAllUser() {
        Cursor cur = null;
        ArrayList<Users> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM USERS", null);
        if (cur.moveToFirst()) {
            do {
                Users u = new Users();
                u.setUsername(cur.getString(0));
                u.setEmail(cur.getString(1));
                u.setNama(cur.getString(2));
                u.setGoldar(cur.getString(3));
                u.setRhesus(cur.getString(4));
                u.setNo_hp(cur.getString(5));
                u.setFoto_profil(cur.getString(6));
                out.add(u);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }

    public void deleteAll() {
        db.delete("users", null, null);
    }
}
