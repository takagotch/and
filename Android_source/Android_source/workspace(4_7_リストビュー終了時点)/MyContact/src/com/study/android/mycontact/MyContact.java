package com.study.android.mycontact;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//My連絡先アクティビティ
public class MyContact extends Activity {

	// データベース関連のフィールドの確保
	MyContactDBHelper helper;
	SQLiteDatabase dbRead;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact);

		// 新規登録ボタンオブジェクトの生成＆取得
		Button newsave = (Button) findViewById(R.id.buttonNewsave);

		// 新規登録ボタンのクリックリスナーのセット
		newsave.setOnClickListener(new onNewSaveClickListener());

		// リストビューセットメソッド
		ListViewSet();

		// リストビューを取得
		ListView listView = (ListView) findViewById(R.id.listView1);

		// リストビューにリストアイテムクリックリスナーをセット
		listView.setOnItemClickListener(new ListItemClickListener());

	}

	// リスタート時（他のアクティビティから戻ってきた時）
	@Override
	protected void onRestart() {
		super.onRestart();

		// リストビューセットメソッド
		ListViewSet();
	}

	// リストビューアイテムクリックリスナー
	class ListItemClickListener implements OnItemClickListener {

		// アイテムクリックメソッド
		public void onItemClick(AdapterView<?> parent, View view, int id,
				long position) {

			// 連絡先表示アクティビティクラスのインテント生成
			Intent intent = new Intent(MyContact.this, MyContactDisp.class);
			// 選択位置を渡す
			intent.putExtra("SELECT", position);
			// アクティビティスタート
			startActivity(intent);

		}
	}

	// クリックリスナー
	class onNewSaveClickListener implements OnClickListener {

		public void onClick(View arg0) {

			// インテントの生成
			Intent intent = new Intent(MyContact.this, MyContactInput.class);

			// 次のアクティビティの開始
			startActivity(intent);
		}
	}

	// リストビューセットメソッド
	public void ListViewSet() {

		// Array Listの作成
		ArrayList<String> alist = new ArrayList<String>();

		// ヘルパークラスのインスタンス生成
		helper = new MyContactDBHelper(this);

		// データベースの取得
		dbRead = helper.getReadableDatabase();

		try {

			Cursor cursor = dbRead.query("MyContactList", ConstDef.columns, null,
					null, null, null, "name");

			while (cursor.moveToNext()) {
				String name = cursor.getString(0);
				alist.add(name);
			}
			cursor.close();

			// アレイアダプタの生成
			ArrayAdapter<String> la = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, alist);

			ListView lv = (ListView) findViewById(R.id.listView1);

			// リストビューにアレイアダプタをセット
			lv.setAdapter(la);

		} catch (Exception e) {
			Toast.makeText(this, "データベースがまだ無いか、読めません", Toast.LENGTH_LONG)
					.show();
		}

		// DBクローズ
		dbRead.close();

	}

	// オプションメニュー
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_my_contact, menu);
		return true;
	}

}
