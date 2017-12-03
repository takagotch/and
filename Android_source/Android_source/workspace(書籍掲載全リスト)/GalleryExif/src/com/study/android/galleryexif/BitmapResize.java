package com.study.android.galleryexif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

//ビットマップリサイズクラス
public class BitmapResize {

	// コンテキスト
	private Context mContext;

	// コンストラクタ
	public BitmapResize(Context context) {
		// コンテキストを保存
		mContext = context;
	}

	// ファイルリサイズ
	public Bitmap FileResize(String path, int width, int height) {

		BitmapFactory.Options opt;

		try {
			// ビットマップファクトリのオプションを取得
			opt = new BitmapFactory.Options();

			// オプションを配置情報の取得だけに設定
			opt.inJustDecodeBounds = true;

			// デコード呼び出し
			BitmapFactory.decodeFile(path, opt);

			// 幅方向のスケール計算
			int scale_w = opt.outWidth / width + 1;
			// 高さ方向のスケール計算
			int scale_h = opt.outHeight / height + 1;
			// 大きい方をスケールに入れておく
			int scale = Math.max(scale_w, scale_h);

			// 新しくビットマップファクトリのオプションを取得
			opt = new BitmapFactory.Options();

			// ビットマップを読みだす指定
			opt.inJustDecodeBounds = false;
			// スケールを設定
			opt.inSampleSize = scale;

			// ビットマップの読み出し
			Bitmap bmp = BitmapFactory.decodeFile(path, opt);

			return bmp;

		} catch (Exception e) {
			Toast.makeText(mContext, "ビットマップ変換エラー", Toast.LENGTH_LONG).show();
			return null;
		}
	}

}
