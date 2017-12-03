package com.study.android.threadsample;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.widget.Toast;

//ループウェイトクラス
public class RoopWait {

	// フィールド
	private int timeCount;
	private Context mContext;
	private Handler handler;

	// コンストラクタ
	public RoopWait(Context act, Handler hnd, int time) {

		// フィールド変数のセット
		mContext = act;
		handler = hnd;
		timeCount = time;
	}

	// 時間待ちループ実行メソッド
	public void execwait() {

		// カウントが０より大きい場合繰り返す
		while (timeCount > 0) {

			// １秒待ち
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}

			// カウントが１０以上なら１０減算
			if (timeCount >= 10)
				timeCount -= 10;

			// カウントが０以上なら
			if (timeCount >= 0) {

				// ハンドラにメッセージを送る
				handler.post(new Runnable() {
					public void run() {
						// Toastを表示
						Toast.makeText(mContext,
								String.valueOf(timeCount) + "秒",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		}

		// 終了音を鳴らす
		ToneGenerator toneGenerator = new ToneGenerator(
				AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
		toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);
		// トーンジェネレータ解放処理
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
		}
		toneGenerator.release();
	}
}
