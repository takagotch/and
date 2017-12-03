package com.study.android.airsword;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.study.android.airsword.AirSwordListener.OnAirSwordListener;

public class AirSword extends Activity {

	// フィールド
	private AirSwordListener asListener;
	public MediaPlayer mdPlayer;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイル設定
		setContentView(R.layout.activity_air_sword);

		// メディアプレーヤーの生成
		mdPlayer = new MediaPlayer();

		// 音声ファイルのパスをセット
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/sample.wav";

		try {
			// データソースのセット
			mdPlayer.setDataSource(path);
			// メディアプレーヤの準備
			mdPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 加速検知リスナー生成
		asListener = new AirSwordListener(this);
		// 加速検知リスナーの定義
		asListener.setOnAirSwordListener(new OnAirSwordListener() {

			// 加速検知メソッド
			public void onAccelero() {
				// 音を鳴らす
				mdPlayer.start();
			}
		});

	}

	// 再生メソッド
	public void onPlay(View v) {
		// 音を鳴らす
		mdPlayer.start();
	}

	// onResume()メソッド
	@Override
	protected void onResume() {
		super.onResume();

		// 加速検知リスナーのレジューム呼び出し
		asListener.onResume();
	}

	// onPause()メソッド
	@Override
	protected void onPause() {
		super.onPause();

		// 加速検知リスナーのポーズ呼び出し
		asListener.onPause();

	}

	// onDestroy()メソッド
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// リリース
		mdPlayer.release();
	}

}