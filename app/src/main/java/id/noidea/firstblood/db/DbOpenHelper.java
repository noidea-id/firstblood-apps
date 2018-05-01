package id.noidea.firstblood.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String LOG = "DbOpenHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fst_bld_db.db";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE USERS(username TEXT PRIMARY KEY, email TEXT NOT NULL," +
            "nama TEXT NOT NULL, goldar TEXT NOT NULL, " +
            "rhesus TEXT NOT NULL, no_hp TEXT NOT NULL," +
            "foto_profil TEXT)";
    private static final String CREATE_TABLE_POSTING = "CREATE TABLE POSTING(" +
            "id_post INTEGER PRIMARY KEY, username TEXT NOT NULL," +
            "nama TEXT NOT NULL, foto_profil TEXT, " +
            "goldar TEXT NOT NULL, rhesus TEXT NOT NULL," +
            "descrip TEXT NOT NULL, rumah_sakit TEXT NOT NULL," +
            "status TEXT NOT NULL, inserted_at TEXT NOT NULL," +
            "updated_at TEXT NOT NULL)";


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_POSTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS POSTING");
        onCreate(db);
    }
}
