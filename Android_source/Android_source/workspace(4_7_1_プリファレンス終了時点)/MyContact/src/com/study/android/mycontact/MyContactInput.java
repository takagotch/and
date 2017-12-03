package com.study.android.mycontact;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

//My連絡先入力アクティビティ
public class MyContactInput extends Activity {

	// プリファレンス確保
	private SharedPreferences pref;

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

		// プリファレンスの取得
		pref = getSharedPreferences("MyContacts", MODE_PRIVATE);
	}

	// 登録ボタンのクリックリスナークラス
	class onSaveClickListener implements OnClickListener {

		// 登録ボタン押下時の処理
		public void onClick(View arg0) {

			// エディットテキストの取得
			EditText name = (EditText) findViewById(R.id.editText1);
			EditText addres = (EditText) findViewById(R.id.editText2);
			EditText tel = (EditText) findViewById(R.id.editText3);
			EditText url = (EditText) findViewById(R.id.editText4);
			// ラジオボタンの取得
			RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup1);
			RadioButton gender = (RadioButton) findViewById(radio
					.getCheckedRadioButtonId());
			// スピナーの取得
			Spinner blood = (Spinner) findViewById(R.id.spinner1);

			// プリファレンスの取得
			SharedPreferences.Editor edit = pref.edit();

			// プリファレンスに保存
			edit.putString("NAME", name.getText().toString());
			edit.putString("ADDRESS", addres.getText().toString());
			edit.putString("TEL", tel.getText().toString());
			edit.putString("URL", url.getText().toString());
			edit.putString("GENDER", gender.getText().toString());
			edit.putString("BLOOD", blood.getSelectedItem().toString());

			// コミット
			edit.commit();

			// アクティビティ終了
			finish();
		}

	}

}
