package com.study.android.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

//HelloWorldアクティビティ
public class HelloWorld extends Activity {

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {

		// スーパークラス呼び出し
		super.onCreate(savedInstanceState);

		// レイアウトファイルのセット
		setContentView(R.layout.activity_hello_world);

		// ボタンオブジェクトの生成
		Button button = (Button) findViewById(R.id.button1);

		// クリックリスナーのセット
		button.setOnClickListener(new ButtonClickListener());
	}

	// クリックリスナークラス
	private class ButtonClickListener implements OnClickListener {

		// onClickメソッド
		public void onClick(View arg0) {
			Toast.makeText(HelloWorld.this, "ボタンが押されました", Toast.LENGTH_LONG)
					.show();
		}

	}

	// 設定メニューのメソッド：今回は使わない
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}

}
