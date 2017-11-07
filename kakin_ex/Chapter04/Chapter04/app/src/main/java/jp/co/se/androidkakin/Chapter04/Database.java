package jp.co.se.androidkakin.Chapter04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
    private static final String DATABASE_NAME = "purchase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PURCHASED_ITEMS_TABLE_NAME = "purchased";

    public static final String PURCHASED_ITEM_ID_COL = "itemId";
    public static final String PURCHASED_QUANTITY_COL = "quantity";

    private static final String[] PURCHASED_COLUMNS = {
        PURCHASED_ITEM_ID_COL, // アイテムID
        PURCHASED_QUANTITY_COL // 購入数
    };

    private SQLiteDatabase mDb;
    private DatabaseHelper mDatabaseHelper;

    public Database(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
        mDb = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
    }

    // 指定されたアイテムの購入数をインクリメントする
    public synchronized void addItem(String itemId) {
        int quantity = 0;

        Cursor cursor = mDb.query(PURCHASED_ITEMS_TABLE_NAME, PURCHASED_COLUMNS, "itemId = ?",
                new String[]{ "" + itemId }, null, null, null);
        if (cursor == null) {
            return;
        }

        if (cursor.getCount() > 0) {
            // データベースから購入数を取得する
            cursor.moveToFirst();
            quantity = cursor.getInt(1);
        }
        
        // 購入数をインクリメントする
        quantity++;

        // データベースを更新する
        ContentValues values = new ContentValues();
        values.put(PURCHASED_ITEM_ID_COL, itemId);
        values.put(PURCHASED_QUANTITY_COL, quantity);
        mDb.replace(PURCHASED_ITEMS_TABLE_NAME, null, values);
    }

    // アイテムの購入数を任意の値で更新する
    public synchronized void updatePurchasedItem(String itemId, int quantity) {
        ContentValues values = new ContentValues();

        // 該当するitemIdの購入数を更新
        values.put(PURCHASED_ITEM_ID_COL, itemId);
        values.put(PURCHASED_QUANTITY_COL, quantity);
        mDb.replace(PURCHASED_ITEMS_TABLE_NAME, null, values);
    }

    // 購入済みプロダクト情報を取得する
    public Cursor queryAllPurchasedItems() {
    	return mDb.query(PURCHASED_ITEMS_TABLE_NAME, PURCHASED_COLUMNS, null,
                null, null, null, null);
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createPurchaseTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion != DATABASE_VERSION) {
                db.execSQL("DROP TABLE IF EXISTS " + PURCHASED_ITEMS_TABLE_NAME);
                createPurchaseTable(db);
                return;
            }
        }

        private void createPurchaseTable(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + PURCHASED_ITEMS_TABLE_NAME + "(" +
                    PURCHASED_ITEM_ID_COL + " TEXT PRIMARY KEY, " +
                    PURCHASED_QUANTITY_COL + " INTEGER)");
        }
    }
}
