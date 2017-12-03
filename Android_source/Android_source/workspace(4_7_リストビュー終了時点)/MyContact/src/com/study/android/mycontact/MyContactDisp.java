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

	// フィールド
	SQLiteDatabase dbRead;
	MyContactDBHelper helper;
	private String tag;

	private String name;
	private String address;
	private String tel;
	private String url;
	private String gender;
	private String blood;

	// onCreateメソッド
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact_disp);

		// インテントの取得
		Bundle extra = getIntent().getExtras();
		// 選択アイテムをDBの_id番号と合わせるため+1
		int num = (int) extra.getLong("SELECT") + 1;

		// ヘルパークラスのインスタンス生成
		helper = new MyContactDBHelper(this);
		// データベースの取得
		dbRead = helper.getReadableDatabase();

		try {

			// MyContackList DBを読み出し、カーソル取得
			Cursor cursor = dbRead.query("MyContactList", ConstDef.columns,
					null, null, null, null, "name");

			// カーソルを_idの数分移動
			for (int i = 0; i < num; i++)
				cursor.moveToNext();

			// DBのデータ取得
			name = cursor.getString(0);
			address = cursor.getString(1);
			tel = cursor.getString(2);
			url = cursor.getString(3);
			gender = cursor.getString(4);
			blood = cursor.getString(5);

			// strに表示用文字列をセット
			String str = getString(R.string.data_name) + "：" + name + "\n"
					+ getString(R.string.data_address) + "：" + address + "\n"
					+ getString(R.string.data_tel) + "：" + tel + "\n"
					+ getString(R.string.data_url) + "：" + url + "\n"
					+ getString(R.string.data_gender) + "：" + gender + "\n"
					+ getString(R.string.data_blood) + "：" + blood + "\n";
			// テキストビューを取得
			TextView textView = (TextView) findViewById(R.id.textDisp);
			textView.setText(str);
			// 名前をタグとして保管（別のメソッドで使う）
			tag = name;

			cursor.close();
		} catch (Exception e) {
			Toast.makeText(this, "データベースの読み出しに失敗しました", Toast.LENGTH_LONG)
					.show();
		}

		// DBクローズ
		dbRead.close();
	}

	public void onDelete(View v) {

		// ヘルパークラスのインスタンス生成
		helper = new MyContactDBHelper(this);
		// データベースの取得
		dbRead = helper.getReadableDatabase();

		// 削除のための条件文の作成
		String condition = null;
		condition = "name = '" + tag + "'";

		try {
			// DBから削除
			dbRead.delete("MyContactList", condition, null);

		} catch (Exception e) {
			Toast.makeText(this, "データベースの削除に失敗しました", Toast.LENGTH_LONG)
					.show();
		}

		// DBクローズ
		dbRead.close();
		finish();
	}

	// 終了ボタン押下時処理
	public void onFinish(View v) {
		finish();
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

}
