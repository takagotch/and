package com.study.android.airsword;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class AirSwordListener implements SensorEventListener {

	// 加速度検知しきい値
	private static final float DETECT_ACCELERO = 13.0F;
	private static final int DETECT_ACCELERO_COUNT = 3;

	// フィールド
	private OnAirSwordListener asListener;
	private SensorManager smng;
	private Context context;
	private int counter;

	// コンストラクタ
	public AirSwordListener(Context con) {

		// センサーサービスを取得
		smng = (SensorManager) con.getSystemService(Context.SENSOR_SERVICE);
		context = con;
	}

	// 加速検知リスナー
	public interface OnAirSwordListener {
		// 加速検知メソッドを呼び出す
		void onAccelero();
	}

	// 加速検知リスナー設定
	public void setOnAirSwordListener(OnAirSwordListener listener) {

		// 加速検知リスナーにリスナーをセット
		asListener = listener;
	}

	// レジュームメソッド
	public void onResume() {

		// 加速度センサーリスト取得
		List<Sensor> list = smng.getSensorList(Sensor.TYPE_ACCELEROMETER);

		// リストのサイズが1より小の場合は終了
		if (list.size() < 1)
			return;

		// センサーマネージャーに登録、ディレイ設定
		smng.registerListener(this, list.get(0), SensorManager.SENSOR_DELAY_UI);
	}

	// ポーズメソッド
	public void onPause() {

		// センサーマネージャーの登録解除
		smng.unregisterListener(this);
	}

	// 精度変更処理
	public void onAccuracyChanged(Sensor s, int ac) {

	}

	// センサー検出値変更イベント
	public void onSensorChanged(SensorEvent evt) {

		// 加速度センサー以外の場合は終了
		if (evt.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}

		// 検出値をセット
		float x = Math.abs(evt.values[0]);
		float y = Math.abs(evt.values[1]);
		float z = Math.abs(evt.values[2]);

		// 加速度が所定値より大きいかのチェック
		if ((x > DETECT_ACCELERO) || (y > DETECT_ACCELERO)
				|| (z > DETECT_ACCELERO)) {

			// 検出カウンタカウント
			counter++;
			// 所定回数以上の場合
			if (counter > DETECT_ACCELERO_COUNT) {
				// カウンタを０にリセット
				counter = 0;

				// トースト加速度表示
				Toast.makeText(
						context,
						"加速検知\n" + String.valueOf(x) + "\n" + String.valueOf(y)
								+ "\n" + String.valueOf(z), Toast.LENGTH_SHORT)
						.show();

				// 加速検知メソッド呼び出し
				if (asListener != null) {
					asListener.onAccelero();
				}
			}

		} else {
			// １回でも加速度が小さい場合は０にリセット
			counter = 0;
		}

	}
}
