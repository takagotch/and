package com.study.android.mycontact;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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

			// エディットテキストの取得
			EditText name = (EditText) findViewById(R.id.editText1);
			EditText address = (EditText) findViewById(R.id.editText2);
			EditText tel = (EditText) findViewById(R.id.editText3);
			EditText url = (EditText) findViewById(R.id.editText4);

			// ラジオボタンの取得
			RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup1);
			RadioButton gender = (RadioButton) findViewById(radio
					.getCheckedRadioButtonId());

			// スピナーの取得
			Spinner blood = (Spinner) findViewById(R.id.spinner1);

			// ヘルパークラスインスタンス生成
			MyContactDBHelper helper = new MyContactDBHelper(
					MyContactInput.this);

			try {
				// データベース書き出しオブジェクトの生成
				SQLiteDatabase dbWrite = helper.getWritableDatabase();

				// SQL文の用意
				String sql = "create table if not exists " + "MyContactList"
						+ "(" + "_id integer primary key autoincrement,"
						+ "name text," + "address text," + "tel text,"
						+ "url text," + "gender text," + "blood text" + ");";

				// テーブルの作成
				dbWrite.execSQL(sql);

				ContentValues ctval = new ContentValues();
				ctval.put("name", name.getText().toString());
				ctval.put("address", address.getText().toString());
				ctval.put("tel", tel.getText().toString());
				ctval.put("url", url.getText().toString());
				ctval.put("gender", gender.getText().toString());
				ctval.put("blood", blood.getSelectedItem().toString());
				dbWrite.insert("MyContactList", null, ctval);

				// DBクローズ
				dbWrite.close();

			} catch (Exception e) {
				// Toastを表示
				Toast.makeText(MyContactInput.this, "データベースの保存に失敗しました",
						Toast.LENGTH_SHORT).show();

			}
			// アクティビティ終了
			finish();
		}
	}
}
