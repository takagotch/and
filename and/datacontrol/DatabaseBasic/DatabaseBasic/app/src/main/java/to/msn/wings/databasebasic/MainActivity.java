package to.msn.wings.databasebasic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SimpleDatabaseHelper helper = null;
    private EditText txtIsbn = null;
    private EditText txtTitle = null;
    private EditText txtPrice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SimpleDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        /*try {
            Toast.makeText(this, "接続しました",
                    Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }*/

        helper = new SimpleDatabaseHelper(this);
        txtIsbn = (EditText) findViewById(R.id.txtIsbn);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
    }

    public void onSave(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("isbn", txtIsbn.getText().toString());
            cv.put("title", txtTitle.getText().toString());
            cv.put("price", txtPrice.getText().toString());
            //db.insert("books", null, cv);
            db.insertWithOnConflict("books", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            Toast.makeText(this, "データの登録に成功しました。",
                    Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    // updateメソッドを使う場合
    /*public void onSave(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
        ContentValues cv = new ContentValues();
        cv.put("title", txtTitle.getText().toString());
        cv.put("price", txtPrice.getText().toString());
        String[] params = {txtIsbn.getText().toString()};
        db.update("books", cv, "isbn = ?", params);
        Toast.makeText(this, "データの登録に成功しました。",
                Toast.LENGTH_SHORT).show();
                } finally {
            db.close();
        }
    }*/

    public void onDelete(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String[] params = {txtIsbn.getText().toString()};
            db.delete("books", "isbn = ?", params);
            Toast.makeText(this, "データの削除に成功しました。",
                    Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    public void onSearch(View view) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = null;
        try {
            String[] cols = {"isbn", "title", "price"};
            String[] params = {txtIsbn.getText().toString()};
            cs = db.query("books", cols, "isbn = ?",
                    params, null, null, null, null);
            if (cs.moveToFirst()) {
                txtTitle.setText(cs.getString(1));
                txtPrice.setText(cs.getString(2));
            } else {
                Toast.makeText(this, "データがありません。",
                        Toast.LENGTH_SHORT).show();
            }
        // MoveToFirst ／ MoveToNext メソッドで、結果セットのすべてのデータを読み込む
        /*String msg = "";
        boolean eol = cs.moveToFirst();
        while (eol) {
            msg += cs.getString(1);
            eol = cs.moveToNext();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();*/

        } finally {
            cs.close();
            db.close();
        }
    }
}
