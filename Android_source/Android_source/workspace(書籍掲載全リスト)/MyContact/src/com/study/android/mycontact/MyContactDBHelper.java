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

	// テーブルの作成
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	// データベースの更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {

	}

}
