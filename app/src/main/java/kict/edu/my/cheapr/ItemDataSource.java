package kict.edu.my.cheapr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25/4/2018.
 */

public class ItemDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ITEMNAME };
    public ItemDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public StoreData createItem_name(String item_name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ITEMNAME, item_name);
        long insertId = database.insert(MySQLiteHelper.TABLE_ITEMNAME, null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMNAME,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        StoreData newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }
    public void deleteItem_name(StoreData item_name) {
        String id = item_name.getId();
        System.out.println("Name deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ITEMNAME, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }
    public List<StoreData> getAllItemNames() {
        List<StoreData> item_names = new ArrayList<StoreData>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMNAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StoreData item_name = cursorToComment(cursor);
            item_names.add(item_name);
            cursor.moveToNext();
        }
// make sure to close the cursor
        cursor.close();
        return item_names;
    }
    private StoreData cursorToComment(Cursor cursor) {
        StoreData item_name = new StoreData();
        item_name.setId(cursor.getString(0));
        item_name.setItem_name(cursor.getString(1));
        return item_name;
    }
}
