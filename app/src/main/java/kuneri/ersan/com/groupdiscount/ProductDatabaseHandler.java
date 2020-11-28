package kuneri.ersan.com.groupdiscount;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "ProductDatabase";

    public ProductDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE products " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sellerId INTEGER," +
                "name TEXT, " +
                "image TEXT," +
                "price REAL," +
                "count INTEGER) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS products";
        db.execSQL(sql);

        onCreate(db);
    }

}

