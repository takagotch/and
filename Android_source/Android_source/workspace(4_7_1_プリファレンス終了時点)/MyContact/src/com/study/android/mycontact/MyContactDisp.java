package com.study.android.mycontact;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//My連絡先表示アクティビティ
public class MyContactDisp extends Activity {

	// フィールド
	private SharedPreferences pref;
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

		// プリファレンスの取得
		pref = getSharedPreferences("MyContacts", MODE_PRIVATE);

		// プリファレンスからデータを読み出す
		name = pref.getString("NAME", "取得できません");
		address = pref.getString("ADDRESS", "取得できません");
		tel = pref.getString("TEL", "取得できません");
		url = pref.getString("URL", "取得できません");
		gender = pref.getString("GENDER", "取得できません");
		blood = pref.getString("BLOOD", "取得できません");

		// strに表示用文字列をセット
		String str = getString(R.string.data_name) + "：" + name + "\n"
				+ getString(R.string.data_address) + "：" + address + "\n"
				+ getString(R.string.data_tel) + "：" + tel + "\n"
				+ getString(R.string.data_url) + "：" + url + "\n"
				+ getString(R.string.data_gender) + "：" + gender + "\n"
				+ getString(R.string.data_blood) + "：" + blood + "\n";

		// テキストビューに表示
		TextView txtView = (TextView) findViewById(R.id.textDisp);
		txtView.setText(str);
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

		// プリファレンス編集オブジェクト取得
		SharedPreferences.Editor editor = pref.edit();

		// クリア
		editor.clear();

		// コミット（確定）
		editor.commit();

		// 終了
		finish();
	}

	// 終了ボタン押下時処理
	public void onFinish(View v) {
		// 終了
		finish();
	}

}
