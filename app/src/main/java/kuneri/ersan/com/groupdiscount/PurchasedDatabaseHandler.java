package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PurchasedDatabaseHandler  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "PurchasedDatabase";

    public PurchasedDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE purchased " +
                "(userId INTEGER," +
                "itemId INTEGER," +
                 "count INTEGER) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS purchased";
        db.execSQL(sql);

        onCreate(db);
    }

}
