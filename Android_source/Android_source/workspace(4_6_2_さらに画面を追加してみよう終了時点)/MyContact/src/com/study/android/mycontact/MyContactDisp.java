package com.study.android.mycontact;

import android.app.Activity;
import android.os.Bundle;

//My連絡先表示アクティビティ
public class MyContactDisp extends Activity {

	// onCarete()メソッド
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_my_contact_disp);
	}

}
