package kuneri.ersan.com.groupdiscount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerPurchased extends PurchasedDatabaseHandler{

    public TableControllerPurchased(Context context) {
        super(context);
    }

    public boolean create(ObjectPurchased objectPurchased) {

        ContentValues values = new ContentValues();

        values.put("itemId", objectPurchased.itemId);
        values.put("userId", objectPurchased.userId);
        values.put("count", objectPurchased.count);
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("purchased", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count(int userId) {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM purchased WHERE userId=" + userId;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<Integer> read(int userId) {


        List<Integer> recordsList = new ArrayList<Integer>();

        String sql = "SELECT * FROM purchased WHERE userId = " + userId;

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
        String sql = "SELECT count FROM purchased WHERE userId = " + userId + " AND itemId = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
        } else {
            return 0;
        }
    }


    public boolean delete(int id, int userId) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("purchased", "itemId ='" + id + "'" + " AND  userId ='" + userId + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}
