package com.study.android.mycontact;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//My連絡先アクティビティ
public class MyContact extends Activity {

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact);

		// 新規登録ボタンオブジェクトの生成＆取得
		Button newSave = (Button) findViewById(R.id.buttonNewsave);

		// クリックリスナーのセット
		newSave.setOnClickListener(new onNewSaveClickListener());
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

	// 表示ボタンがクリックされた時のメソッド
	public void onDisp(View v) {

		// インテントの生成
		Intent intent = new Intent(MyContact.this, MyContactDisp.class);

		// 次のアクティビティの開始
		startActivity(intent);

	}

	// オプションメニュー
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_my_contact, menu);
		return true;
	}

}
