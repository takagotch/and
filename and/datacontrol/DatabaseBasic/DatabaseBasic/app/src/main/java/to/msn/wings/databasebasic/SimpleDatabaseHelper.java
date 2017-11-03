package to.msn.wings.databasebasic;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SimpleDatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "sample.sqlite";
    static final private int VERSION = 1;

    public SimpleDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

   @Override
   public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books (" +
                "isbn TEXT PRIMARY KEY, title TEXT, price INTEGER)");
        db.execSQL("INSERT INTO books(isbn, title, price)" +
                " VALUES('978-4-7980-4512-2', 'はじめてのASP.NET Webフォーム', 3000)");
        db.execSQL("INSERT INTO books(isbn, title, price)" +
                " VALUES('978-4-7980-4179-7', 'ASP.NET MVC 5実践プログラミング', 3500)");
        db.execSQL("INSERT INTO books(isbn, title, price)" +
                " VALUES('978-4-7741-8030-4', 'Javaポケットリファレンス ', 2680)");
        db.execSQL("INSERT INTO books(isbn, title, price)" +
                " VALUES('978-4-7741-7984-1', 'Swiftポケットリファレンス', 2780)");
        db.execSQL("INSERT INTO books(isbn, title, price)" +
                " VALUES('978-4-7981-3547-2', '独習PHP 第3版', 3200)");
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books (" +
                "isbn TEXT PRIMARY KEY, title TEXT, price INTEGER)");
        String[] isbns = {"978-4-7980-4512-2",
                "978-4-7741-8030-4", "978-4-7741-8030-4",
                "978-4-7741-7984-1", "978-4-7981-3547-2"};
        String[] titles = {"はじめてのASP.NET Webフォーム",
                "ASP.NET MVC 5実践プログラミング", "Javaポケットリファレンス",
                "Swiftポケットリファレンス", "独習PHP 第3版"};
        int[] prices = {3000, 3500, 2680, 2780, 3200};
        db.beginTransaction();
        try {
            SQLiteStatement sql = db.compileStatement(
                    "INSERT INTO books(isbn, title, price) VALUES(?, ?, ?)");
            for (int i = 0; i < isbns.length; i++) {
                sql.bindString(1, isbns[i]);
                sql.bindString(2, titles[i]);
                sql.bindLong(3, prices[i]);
                sql.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        db.execSQL("DROP TABLE IF EXISTS books");
        onCreate(db);
    }
}
