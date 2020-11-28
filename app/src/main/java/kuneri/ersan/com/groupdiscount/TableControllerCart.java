package kuneri.ersan.com.groupdiscount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TableControllerCart extends CartsDatabaseHandler{
    Context context;
    public TableControllerCart(Context context) {
        super(context);
        this.context = context;
    }

    public boolean create(ObjectCarts objectCarts) {

        ContentValues values = new ContentValues();

        values.put("itemId", objectCarts.itemId);
        values.put("userId", objectCarts.userId);
        values.put("count", objectCarts.count);
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("carts", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count(int userId) {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM carts WHERE userId =" + userId;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<Integer> read(int userId) {


        List<Integer> recordsList = new ArrayList<Integer>();

        String sql = "SELECT * FROM carts WHERE userId = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int itemId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("itemId")));
                recordsList.add(itemId);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public int readCount(int userId, int productId) {
        String sql = "SELECT count FROM carts WHERE userId = " + userId + " AND itemId = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        Toast.makeText(context, String.valueOf(productId), Toast.LENGTH_SHORT).show();
        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
        } else {
            return 0;
        }
    }

    public boolean delete(int id, int userId) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("carts", "itemId ='" + id + "'" + " AND  userId ='" + userId + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

}
