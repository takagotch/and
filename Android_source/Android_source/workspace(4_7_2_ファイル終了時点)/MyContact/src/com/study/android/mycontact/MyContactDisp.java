package com.study.android.mycontact;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//My連絡先表示アクティビティ
public class MyContactDisp extends Activity {

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

		try {
			// Inputファイルストリーム生成
			FileInputStream infile = openFileInput("MyContact.txt");
			// 読み出しバッファ生成
			BufferedReader rdBuff = new BufferedReader(new InputStreamReader(
					infile));

			// データ読み出し
			name = rdBuff.readLine();
			address = rdBuff.readLine();
			tel = rdBuff.readLine();
			url = rdBuff.readLine();
			gender = rdBuff.readLine();
			blood = rdBuff.readLine();

			rdBuff.close();

		} catch (Exception e) {
			// Toastを表示
			Toast.makeText(MyContactDisp.this, "ファイル読み出しに失敗しました",
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
		TextView textView = (TextView) findViewById(R.id.textVDisp);
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

		try {
			// ファイル削除
			deleteFile("MyContact.txt");
		} catch (Exception e) {
			// Toastを表示
			Toast.makeText(MyContactDisp.this, "削除に失敗しました", Toast.LENGTH_LONG)
					.show();
		}

		// 終了
		finish();
	}

	// 終了ボタン押下時処理
	public void onFinish(View v) {
		// 終了
		finish();
	}
}
