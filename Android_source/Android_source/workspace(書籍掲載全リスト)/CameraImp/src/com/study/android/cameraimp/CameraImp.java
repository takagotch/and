package com.study.android.cameraimp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

//CameraImpメインアクティビティ
public class CameraImp extends Activity {

	private FrameLayout frameLayout; // フレームレイアウト
	private SurfaceView cameraView; // カメラプレビュー用サーフェスビュー
	private ImprintView imprintView; // 埋め込み文字用ビュー

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// フルスクリーンにする
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// タイトルバーを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 各ビューの取得
		frameLayout = new FrameLayout(this);
		cameraView = new CameraView(this);
		imprintView = new ImprintView(this);

		// ビューを重ねる
		frameLayout.addView(cameraView); // カメラ用ビューを追加
		frameLayout.addView(imprintView); // 埋め込み文字用ビューを追加。重ねる

		// フレームレイアウト全体ビューをセット
		setContentView(frameLayout);

	}

	// オプションメニュークリエイト
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_camera_imp, menu);

		return super.onCreateOptionsMenu(menu);
	}

	// オプションメニューのアイテム選択時メソッド
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// 設定メニューが選択されている場合の処理
		if (item.getItemId() == R.id.menu_settings) {

			// 埋め込み文字設定アクティビティの開始
			startActivity(new Intent(this, SetImpText.class));
		}
		return super.onOptionsItemSelected(item);
	}

}
