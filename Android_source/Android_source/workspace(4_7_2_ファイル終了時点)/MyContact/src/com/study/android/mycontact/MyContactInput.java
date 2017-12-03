package com.study.android.mycontact;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
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
			EditText addres = (EditText) findViewById(R.id.editText2);
			EditText tel = (EditText) findViewById(R.id.editText3);
			EditText url = (EditText) findViewById(R.id.editText4);

			// ラジオボタンの取得
			RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup1);
			RadioButton gender = (RadioButton) findViewById(radio
					.getCheckedRadioButtonId());

			// スピナーの取得
			Spinner blood = (Spinner) findViewById(R.id.spinner1);

			try {
				// Outputストリーム生成
				FileOutputStream outFile = openFileOutput(
						"MyContact.txt", MODE_PRIVATE);

				// 書き込みバッファ生成
				BufferedWriter wrBuff = new BufferedWriter(
						new OutputStreamWriter(outFile));

				// 書き込み
				wrBuff.write(name.getText().toString() + "\r\n"
						+ addres.getText().toString() + "\r\n"
						+ tel.getText().toString() + "\r\n"
						+ url.getText().toString() + "\r\n"
						+ gender.getText().toString() + "\r\n"
						+ blood.getSelectedItem().toString() + "\r\n");
				// クローズ
				wrBuff.close();
			} catch (Exception e) {
				// Toastを表示
				Toast.makeText(MyContactInput.this, "保存に失敗しました",
						Toast.LENGTH_SHORT).show();
			}

			// アクティビティ終了
			finish();
		}

	}

}
