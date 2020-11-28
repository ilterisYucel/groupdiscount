package kuneri.ersan.com.groupdiscount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerProduct extends ProductDatabaseHandler {

    public TableControllerProduct(Context context) {
        super(context);
    }

    public boolean create(ObjectProduct objectProduct) {

        ContentValues values = new ContentValues();

        values.put("sellerId", objectProduct.sellerId);
        values.put("name", objectProduct.name);
        values.put("image", objectProduct.image);
        values.put("price", objectProduct.price);
        values.put("count", objectProduct.count);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("products", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM products";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public int countSellerItems(Integer sellerId){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM products WHERE sellerId =" + sellerId;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;


    }

    public List<ObjectProduct> read() {

        List<ObjectProduct> recordsList = new ArrayList<ObjectProduct>();

        String sql = "SELECT * FROM products ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("name"));
                String productİmage = cursor.getString(cursor.getColumnIndex("image"));
                Double productPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                Integer productCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));

                ObjectProduct objectProduct = new ObjectProduct();
                objectProduct.id = id;
                objectProduct.name = productName;
                objectProduct.image = productİmage;
                objectProduct.price = productPrice;
                objectProduct.count = productCount;

                recordsList.add(objectProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public List<ObjectProduct> read(String sortBy) {

        List<ObjectProduct> recordsList = new ArrayList<ObjectProduct>();

        String sql;
        if (sortBy.equals("recent")) {
            sql = "SELECT * FROM products ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM products ORDER BY " + sortBy + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("name"));
                String productİmage = cursor.getString(cursor.getColumnIndex("image"));
                Double productPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                Integer productCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));

                ObjectProduct objectProduct = new ObjectProduct();
                objectProduct.id = id;
                objectProduct.name = productName;
                objectProduct.image = productİmage;
                objectProduct.price = productPrice;
                objectProduct.count = productCount;

                recordsList.add(objectProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public ObjectProduct readSingleRecord(int productId) {

        ObjectProduct objectProduct = null;

        String sql = "SELECT * FROM products WHERE id = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            Double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
            Integer count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));

            objectProduct = new ObjectProduct();
            objectProduct.id = id;
            objectProduct.name = name;
            objectProduct.image = image;
            objectProduct.price = price;
            objectProduct.count = count;

        }

        cursor.close();
        db.close();

        return objectProduct;

    }


    public List<ObjectProduct> readBuyedItems(Integer sellerId) {

        List<ObjectProduct> recordsList = new ArrayList<ObjectProduct>();

        String sql = "SELECT * FROM products WHERE sellerId = " + sellerId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("name"));
                String productİmage = cursor.getString(cursor.getColumnIndex("image"));
                Double productPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                Integer productCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));

                ObjectProduct objectProduct = new ObjectProduct();
                objectProduct.id = id;
                objectProduct.name = productName;
                objectProduct.image = productİmage;
                objectProduct.price = productPrice;
                objectProduct.count = productCount;

                recordsList.add(objectProduct);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public List<ObjectProduct> readSellItems(List<Integer> itemIds){
        List<ObjectProduct> recordsList = new ArrayList<ObjectProduct>();

        //String sql = "SELECT * FROM carts INNER JOIN products ON carts.itemId = products.id WHERE userId = "+userId;


        for(int i = 0; i < itemIds.size(); i++){

            String sql = "SELECT * FROM products WHERE id = " +  itemIds.get(i);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String productName = cursor.getString(cursor.getColumnIndex("name"));
                String productİmage = cursor.getString(cursor.getColumnIndex("image"));
                Double productPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                Integer productCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));

                ObjectProduct objectProduct = new ObjectProduct();
                objectProduct.id = id;
                objectProduct.name = productName;
                objectProduct.image = productİmage;
                objectProduct.price = productPrice;
                objectProduct.count = productCount;

                recordsList.add(objectProduct);

                cursor.close();
                db.close();

            }

        }



        return recordsList;
    }


    public boolean update(ObjectProduct objectProduct) {

        ContentValues values = new ContentValues();

        values.put("name", objectProduct.name);
        values.put("image", objectProduct.image);
        values.put("price", objectProduct.price);
        values.put("count", objectProduct.count);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectProduct.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("products", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("products", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public ObjectProduct search(String name){
        ObjectProduct objectProduct = null;

        String sql = "SELECT * FROM products WHERE name = '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String pName = cursor.getString(cursor.getColumnIndex("name"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            Double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
            Integer count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
            objectProduct = new ObjectProduct();
            objectProduct.id = id;
            objectProduct.name = pName;
            objectProduct.image = image;
            objectProduct.price = price;
            objectProduct.count = count;

        }

        cursor.close();
        db.close();

        return objectProduct;

    }


}
