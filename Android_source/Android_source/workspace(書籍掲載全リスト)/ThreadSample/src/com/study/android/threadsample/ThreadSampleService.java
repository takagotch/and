package com.study.android.threadsample;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

//サービスクラス
public class ThreadSampleService extends Service {

	// フィールド
	private int timeCount;
	Handler handler;
	Thread thread;

	// バインド
	@Override
	public IBinder onBind(Intent arg0) {

		// Toastに表示
		Toast.makeText(this, "サービスのonBindが呼び出されました", Toast.LENGTH_SHORT).show();

		return null;
	}

	// onCreate()メソッド
	@Override
	public void onCreate() {
		super.onCreate();

		// Toastに表示
		Toast.makeText(this, "サービスのonCreateが呼び出されました", Toast.LENGTH_SHORT)
				.show();

		// ハンドラーの生成
		handler = new Handler();
	}

	// スタート
	@Override
	public void onStart(Intent intent, int Id) {
		super.onStart(intent, Id);

		// Toastに表示
		Toast.makeText(this, "サービスのonStartが呼び出されました", Toast.LENGTH_SHORT)
				.show();

		// インテントで渡されるバンドル取得
		Bundle bundle = intent.getExtras();
		// カウントにインテントで受け取った文字列を数値にして秒単位に修正
		timeCount = Integer.parseInt(bundle.getString("ALARMTIME")) * 60;

		// スレッド
		thread = new Thread(new Runnable() {

			// 実行内容
			public void run() {

				// ループウェイト生成
				RoopWait rop = new RoopWait(ThreadSampleService.this, handler,
						timeCount);
				// ウェイト実行
				rop.execwait();

			}
		});
		// スレッドスタート
		thread.start();

	}

	// 終了時
	@Override
	public void onDestroy() {
		super.onDestroy();

		// スレッドクリア
		thread = null;

		// Toastに表示
		Toast.makeText(this, "サービスのonDestroyが呼び出されました", Toast.LENGTH_SHORT)
				.show();

	}

}
