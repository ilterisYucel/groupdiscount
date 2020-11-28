package kuneri.ersan.com.groupdiscount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerUser extends UserDatabaseHandler{

    public TableControllerUser(Context context) {
        super(context);
    }

    public boolean create(ObjectUser objectUser) {

        ContentValues values = new ContentValues();

        values.put("name", objectUser.name);
        values.put("image", objectUser.image);
        values.put("email", objectUser.email);
        values.put("password", objectUser.password);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("users", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public List<ObjectUser> read() {

        List<ObjectUser> recordsList = new ArrayList<ObjectUser>();

        String sql = "SELECT * FROM users ORDER BY id ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("name"));
                String productİmage = cursor.getString(cursor.getColumnIndex("image"));
                String productPrice = cursor.getString(cursor.getColumnIndex("email"));
                String  productCount = cursor.getString(cursor.getColumnIndex("password"));

                ObjectUser objectProduct = new ObjectUser();
                objectProduct.id = id;
                objectProduct.name = productName;
                objectProduct.image = productİmage;
                objectProduct.email = productPrice;
                objectProduct.password = productCount;

                recordsList.add(objectProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public List<ObjectUser> read(String sortBy) {

        List<ObjectUser> recordsList = new ArrayList<ObjectUser>();

        String sql;
        if (sortBy.equals("newest")) {
            sql = "SELECT * FROM users ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM users ORDER BY " + sortBy + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("name"));
                String productİmage = cursor.getString(cursor.getColumnIndex("image"));
                String productPrice = cursor.getString(cursor.getColumnIndex("email"));
                String  productCount = cursor.getString(cursor.getColumnIndex("password"));

                ObjectUser objectProduct = new ObjectUser();
                objectProduct.id = id;
                objectProduct.name = productName;
                objectProduct.image = productİmage;
                objectProduct.email = productPrice;
                objectProduct.password = productCount;

                recordsList.add(objectProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM users";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }


    public boolean update(ObjectUser objectUser) {

        ContentValues values = new ContentValues();

        values.put("name", objectUser.name);
        values.put("image", objectUser.image);
        values.put("email", objectUser.email);
        values.put("password", objectUser.password);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectUser.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("users", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("users", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public int searchName(String name){
        //ObjectUser objectUser = null;
        int userID = -1;

        String sql = "SELECT * FROM users WHERE name = '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            userID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        }

        cursor.close();
        db.close();

        return userID;

    }
    public int searchEmail(String email){
        //ObjectUser objectUser = null;
        int userID = -1;

        String sql = "SELECT * FROM users WHERE email = '" + email + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            userID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        }

        cursor.close();
        db.close();

        return userID;

    }

    public int controlUser(String username, String password){
        int userID = -1;
        String sql = "SELECT * FROM users WHERE name = '" + username + "' AND password = '" + password + "'";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            userID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        }

        cursor.close();
        db.close();

        return userID;

    }

    public String searchID(int id){
        String username = "Custom User";

        String sql = "SELECT * FROM users WHERE id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            username = cursor.getString(cursor.getColumnIndex("name"));
        }

        cursor.close();
        db.close();

        return username;

    }

    public ObjectUser getObjWithName(String name){
        //ObjectUser objectUser = null;
        ObjectUser obj = new ObjectUser();

        String sql = "SELECT * FROM users WHERE name = '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.email = cursor.getString(cursor.getColumnIndex("email"));
            obj.password = cursor.getString(cursor.getColumnIndex("password"));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
        }else {
            obj = null;
        }

        cursor.close();
        db.close();

        return obj;

    }
}
