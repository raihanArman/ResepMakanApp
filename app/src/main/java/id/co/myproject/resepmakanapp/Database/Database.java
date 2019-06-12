package id.co.myproject.resepmakanapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {
    public static final String DB_NAME = "ratingDB.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public void tambahRating(String id_makanan, String rate){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO rating (id_makanan, rate) VALUES ('%s', '%s');",
                id_makanan, rate);
        db.execSQL(query);
    }

    public boolean cekRating(String id_makanan){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM rating WHERE id_makanan = '%s'", id_makanan);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }
}
