package kict.edu.my.cheapr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 25/4/2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_ITEMNAME = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMNAME = "comment";
    private static final String DATABASE_ITEMNAME = "name.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ITEMNAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ITEMNAME
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_ITEMNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMNAME);
        onCreate(db);
    }
}
