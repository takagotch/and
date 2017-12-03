package com.study.android.mycontact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

//My連絡先表示アクティビティ
public class MyContactDisp extends Activity {

	// フィールド
	private String tel;
	private String address;
	private String url;

	// onCarete()メソッド
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact_disp);

		// String.xmlの文字列からセット
		tel = getString(R.string.def_tel);
		address = getString(R.string.def_address);
		url = getString(R.string.def_url);
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
