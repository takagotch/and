package com.study.android.mycontact;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//連絡先アプリメインアクティビティ
public class MyContact extends Activity {

	// フィールド
	public SharedPreferences pref;
	// データベース関連のフィールド
	SQLiteDatabase dbRead;
	MyContactDBHelper helper;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルの設定
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

	// リスタート時（他のアクティビティから戻ってきたとき）
	@Override
	protected void onRestart() {
		super.onRestart();

		// リストビューセットメソッド
		ListViewSet();
	}

	// リストビューセットメソッド
	public void ListViewSet() {

		// Array Listの作成
		ArrayList<String> arrayList = new ArrayList<String>();

		// ヘルパークラスのインスタンス生成
		helper = new MyContactDBHelper(this);

		// データベースの取得
		dbRead = helper.getReadableDatabase();

		try {

			Cursor cursor = dbRead.query("MyContactList", ConstDef.columns,
					null, null, null, null, "name");

			while (cursor.moveToNext()) {
				String name = cursor.getString(0);
				arrayList.add(name);
			}
			cursor.close();

			// アレイアダプタの生成
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, arrayList);

			ListView listView = (ListView) findViewById(R.id.listView1);
			// リストビューにアレイアダプタをセット
			listView.setAdapter(arrayAdapter);

		} catch (Exception e) {
			Toast.makeText(this, "データベースがまだ無いか、読めません", Toast.LENGTH_LONG)
					.show();
		}

		// DBクローズ
		dbRead.close();

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

	// オプションメニュー
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_my_contact, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// オプションメニューのアイテム選択時メソッド
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// 削除メニューが選択されている場合の処理
		if (item.getItemId() == R.id.menu_delete) {

			try {
				// ヘルパークラスのインスタンス生成
				helper = new MyContactDBHelper(MyContact.this);
				// データベースの取得
				dbRead = helper.getReadableDatabase();

				// SQL文の作成
				dbRead.execSQL("drop table if exists MyContactList;");

				// DBクローズ
				dbRead.close();
			} catch (Exception e) {
				// Toastを表示
				Toast.makeText(this, "削除に失敗しました", Toast.LENGTH_SHORT).show();
			}

			// Array Listの作成
			ArrayList<String> arrayList = new ArrayList<String>();
			// アレイアダプタの生成
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, arrayList);
			// アレイアダプタのクリア
			arrayAdapter.clear();

			// リストビュー取得
			ListView listView = (ListView) findViewById(R.id.listView1);
			// リストビューにアレイアダプタをセット
			listView.setAdapter(arrayAdapter);

		}
		// アプリ情報メニューが選択されている場合の処理
		else if (item.getItemId() == R.id.menu_apliinfo) {

			// パッケージマネージャの生成
			PackageManager pkm = getPackageManager();
			// パッケージ情報の確保
			PackageInfo info = null;

			try {
				// パッケージ情報の取得
				info = pkm.getPackageInfo("com.study.android.mycontact",
						PackageManager.GET_INSTRUMENTATION);
			} catch (NameNotFoundException e) {
			}

			// ダイアログで表示
			// アラートダイアログの生成
			AlertDialog.Builder dlog = new AlertDialog.Builder(this);
			// タイトルをstrings.xmlからセット
			dlog.setTitle(R.string.menu_apliinfo);
			// バージョンの表示
			dlog.setMessage("VersionCode:" + String.valueOf(info.versionCode)
					+ " versionName:" + info.versionName);
			// ボタンの表示
			dlog.setPositiveButton("OK", null);
			// ダイアログの表示
			dlog.show();
		}

		return super.onOptionsItemSelected(item);
	}

}
