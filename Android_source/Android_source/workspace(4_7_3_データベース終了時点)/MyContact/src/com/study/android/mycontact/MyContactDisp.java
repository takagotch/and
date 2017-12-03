package com.study.android.mycontact;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//My連絡先表示アクティビティ
public class MyContactDisp extends Activity {

	// データベース関連のフィールドの確保
	MyContactDBHelper helper;
	SQLiteDatabase dbRead;

	// フィールド
	private String name;
	private String tel;
	private String address;
	private String url;
	private String gender;
	private String blood;

	// onCarete()メソッド
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact_disp);

		// ヘルパークラスのインスタンス生成
		helper = new MyContactDBHelper(this);
		// データベースの取得
		dbRead = helper.getReadableDatabase();

		try {
			// SQL文の作成
			String sql = "select * from MyContactList;";
			// SQL文の実行
			Cursor cursor = dbRead.rawQuery(sql, null);

			// データ読み出し
			cursor.moveToFirst();
			name = cursor.getString(0);
			address = cursor.getString(1);
			tel = cursor.getString(2);
			url = cursor.getString(3);
			gender = cursor.getString(4);
			blood = cursor.getString(5);

		} catch (Exception e) {
			// Toastを表示
			Toast.makeText(MyContactDisp.this, "データベースの読み出しに失敗しました",
					Toast.LENGTH_LONG).show();
		}

		// strに表示用文字列をセット
		String str = getString(R.string.data_name) + "：" + name + "\n"
				+ getString(R.string.data_address) + "：" + address + "\n"
				+ getString(R.string.data_tel) + "：" + tel + "\n"
				+ getString(R.string.data_url) + "：" + url + "\n"
				+ getString(R.string.data_gender) + "：" + gender + "\n"
				+ getString(R.string.data_blood) + "：" + blood + "\n";

		// テキストビューに表示
		TextView textView = (TextView) findViewById(R.id.textDisp);
		textView.setText(str);
	}

	// 地図ボタン押下時処理
	public void onMap(View v) {
		// 暗黙的インテント生成（ビュー + 位置）
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
				+ address));
		// アクティビティスタート
		startActivity(intent);
	}

	// 電話ボタン押下時処理
	public void onTel(View v) {
		// 暗黙的インテント生成（コール + tel）
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
		// アクティビティスタート
		startActivity(intent);
	}

	// ブラウザボタン押下時処理
	public void onWeb(View v) {
		// 暗黙的インテント生成（ビュー + URI）
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		// アクティビティスタート
		startActivity(intent);
	}

	// 削除ボタン押下時処理
	public void onDelete(View v) {

		// ヘルパークラスのインスタンス生成
		helper = new MyContactDBHelper(this);
		// データベースの取得
		dbRead = helper.getReadableDatabase();

		try {
			// SQL文の作成
			dbRead.execSQL("drop table if exists MyContactList;");

		} catch (Exception e) {
			Toast.makeText(this, "データベースの削除に失敗しました", Toast.LENGTH_LONG).show();
		}

		// DBクローズ
		dbRead.close();

		// 終了
		finish();
	}

	// 終了ボタン押下時処理
	public void onFinish(View v) {
		// 終了
		finish();
	}
}
