package com.study.android.mycontact;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//My連絡先入力アクティビティ
public class MyContactInput extends Activity {

	// onCreate()メソッド
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact_input);

		// 登録ボタンオブジェクトの取得
		Button save = (Button) findViewById(R.id.button1);
		// クリックリスナーの生成と登録
		save.setOnClickListener(new onSaveClickListener());
	}

	// 登録ボタンのクリックリスナークラス
	class onSaveClickListener implements OnClickListener {

		// 登録ボタン押下時の処理
		public void onClick(View arg0) {

			// アクティビティ終了
			finish();
		}

	}

}
