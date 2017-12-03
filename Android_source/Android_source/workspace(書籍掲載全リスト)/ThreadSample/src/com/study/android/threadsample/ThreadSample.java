package com.study.android.threadsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

//スレッドサンプルアクティビティ
public class ThreadSample extends Activity {

	// フィールド
	private int setTime;
	private Handler handler;
	Thread thread;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_thread_sample);

		// ハンドラーの生成
		handler = new Handler();
	}

	// スレッド起動ボタン押下時の処理
	public void onThreadStart(View v) {

		// スピナーを取得
		Spinner alarmTime = (Spinner) findViewById(R.id.spinner1);
		// スピナー選択アイテムの数を数値にして秒単位にしてsetTimeにセット
		setTime = Integer.valueOf(alarmTime.getSelectedItem().toString()) * 60;

		// Toastに表示
		Toast.makeText(this, "Start" + String.valueOf(setTime) + "秒",
				Toast.LENGTH_SHORT).show();

		// スレッド
		thread = new Thread(new Runnable() {

			// 実行内容
			public void run() {

				// ループウェイト生成
				RoopWait rop = new RoopWait(ThreadSample.this, handler, setTime);
				// ループウェイト実行
				rop.execwait();

			}
		});
		// スレッドスタート
		thread.start();
	}

	// 非同期タスク起動ボタン押下時の処理
	public void onAsyncTaskStart(View v) {

		// スピナーを取得
		Spinner alarmTime = (Spinner) findViewById(R.id.spinner1);

		// スピナー選択アイテムの数を数値にして秒単位にしてsetTimeにセット
		setTime = Integer.valueOf(alarmTime.getSelectedItem().toString()) * 60;

		// Toastに表示
		Toast.makeText(this, "Start " + String.valueOf(setTime) + "秒",
				Toast.LENGTH_SHORT).show();

		// 非同期タスクの生成
		ThreadSampleAsync thread = new ThreadSampleAsync(this, handler,
				String.valueOf(setTime));

		// 非同期タスク実行
		thread.execute();
	}

	// サービス起動ボタン押下時の処理
	public void onServiceStart(View v) {

		// インテントを取得
		Intent intent = new Intent(this, ThreadSampleService.class);
		// スピナーを取得
		Spinner alarmTime = (Spinner) findViewById(R.id.spinner1);

		// スピナー選択アイテムの数を数値にして秒単位にしてsetTimeにセット
		setTime = Integer.valueOf(alarmTime.getSelectedItem().toString()) * 60;

		// Toastに表示
		Toast.makeText(this, "Start " + String.valueOf(setTime) + "秒",
				Toast.LENGTH_SHORT).show();

		// スピナー選択値を文字列でインテントで渡す
		intent.putExtra("ALARMTIME", alarmTime.getSelectedItem().toString());

		// サービススタート
		startService(intent);
	}

	// アクティビティ終了時
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// スレッドのクリア
		thread = null;
	}
}
