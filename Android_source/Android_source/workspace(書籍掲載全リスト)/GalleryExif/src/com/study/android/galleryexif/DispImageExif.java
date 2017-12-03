package com.study.android.galleryexif;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//画像Exifh表示アクティビティ
public class DispImageExif extends Activity {

	// 画像表示サイズ
	private final int LARGE_WIDTH = 200;
	private final int LARGE_HIGHT = 200;

	// フィールド
	private ImageView imageView;
	private TextView textView;

	// onCreateメソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルセット
		setContentView(R.layout.activity_disp_img_exif);

		// イメージビューの取得
		imageView = (ImageView) findViewById(R.id.imageView1);
		// テキストビューの取得
		textView = (TextView) findViewById(R.id.textView1);

		// インテント呼び出し時のFILE_PATHタグの文字をファイルパスにセット
		String fpath = getIntent().getStringExtra("FILE_PATH");
		// ビットマップリサイズオブジェクト生成
		BitmapResize brsz = new BitmapResize(this);
		// インテントで渡されたファイルパスのビットマップをリサイズ
		Bitmap bmp = brsz.FileResize(fpath, LARGE_WIDTH, LARGE_HIGHT);
		// イメージビューにビットマップをセット
		imageView.setImageBitmap(bmp);

		// Exif表示
		try {
			// Exifインターフェース生成
			ExifInterface exifInterface = new ExifInterface(fpath);
			// テキストビューにExif表示
			textView.setText(getExifData(exifInterface));

		} catch (IOException e) {
			Toast.makeText(this, "Exif取得エラー", Toast.LENGTH_LONG).show();
		}

	}

	// ボタンクリック時メソッド
	public void onSelectButton(View view) {
		// アクティビティ終了
		finish();
	}

	// Exifデータ取得
	private String getExifData(ExifInterface ei) {

		// Exifデータを文字列にセット

		// API 5;
		String exif = "＜Exif情報＞\n" + "画像の高さ: "
				+ ei.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "\n"
				+ "画像の幅: " + ei.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
				+ "\n" + "画像方向: "
				+ ei.getAttribute(ExifInterface.TAG_ORIENTATION) + "\n"
				+ "ファイル変更日時: " + ei.getAttribute(ExifInterface.TAG_DATETIME)
				+ "\n" + "画像入力機器のメーカ名: "
				+ ei.getAttribute(ExifInterface.TAG_MAKE) + "\n"
				+ "画像入力機器のモデル名: " + ei.getAttribute(ExifInterface.TAG_MODEL)
				+ "\n" + "フラッシュ: " + ei.getAttribute(ExifInterface.TAG_FLASH)
				+ "\n" + "焦点距離: "
				+ ei.getAttribute(ExifInterface.TAG_FOCAL_LENGTH) + "\n"
				+ "ホワイトバランス: "
				+ ei.getAttribute(ExifInterface.TAG_WHITE_BALANCE) + "\n"
				+ "緯度: " + ei.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
				+ "\n" + "経度: "
				+ ei.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) + "\n"
				+ "GPS時間: " + ei.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)
				+ "\n" + "測位方式の名称: "
				+ ei.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD)
				+ "\n" + "GPS日付: "
				+ ei.getAttribute(ExifInterface.TAG_GPS_DATESTAMP) + "\n";

		// // API 9
		// + "高度: " + ei.getAttribute(ExifInterface.TAG_GPS_ALTITUDE) + "\n"
		// + "高度の基準: " + ei.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF) +
		// "\n"
		//
		// // API11
		// + "露出時間: " + ei.getAttribute(ExifInterface.TAG_EXPOSURE_TIME) + "\n"
		// + "絞り値: " + ei.getAttribute(ExifInterface.TAG_APERTURE) + "\n"
		// + "ISOスピード: " + ei.getAttribute(ExifInterface.TAG_ISO) + "\n"
		// ;

		return exif;
	}

}
