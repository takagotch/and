package com.study.android.threadsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Handler;

//非同期タスククラス
public class ThreadSampleAsync extends AsyncTask<Void, Void, String> {

	// フィールド
	private int timeCount;
	private Activity activity;
	private Handler handler;

	// コンストラクタ
	public ThreadSampleAsync(Activity act, Handler hnd, String time) {

		// アクティビティの保存
		activity = act;
		handler = hnd;
		timeCount = Integer.valueOf(time);
	}

	// 前処理
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// ダイアログ生成
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		dialog.setTitle("非同期タスク起動");
		dialog.setMessage("確認：ボタン押下");
		dialog.setPositiveButton("OK", null);
		dialog.show();

	}

	// バックグラウンド処理
	@Override
	protected String doInBackground(Void... params) {

		String ret = null;

		RoopWait rop = new RoopWait(activity, handler, timeCount);
		rop.execwait();

		return ret;
	}

	// 後処理
	@Override
	protected void onPostExecute(String ret) {
		super.onPostExecute(ret);

		// ダイアログ生成
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		dialog.setTitle("非同期タスク終了");
		dialog.setMessage("確認：ボタン押下");
		dialog.setPositiveButton("OK", null);
		dialog.show();

	}

}