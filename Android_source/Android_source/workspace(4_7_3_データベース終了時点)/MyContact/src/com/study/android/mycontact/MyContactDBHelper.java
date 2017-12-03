package com.study.android.mycontact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//My連絡先DBヘルパークラスMyContactDBHelper
public class MyContactDBHelper extends SQLiteOpenHelper {

	// ヘルパークラスコンストラクタ
	public MyContactDBHelper(Context context) {
		super(context, "MyContact", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
