package com.study.android.cameraimp;

import java.io.FileOutputStream;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

//カメラビュー
public class CameraView extends SurfaceView implements SurfaceHolder.Callback,
		Camera.PictureCallback {

	private SurfaceHolder surfaceHolder; // サーフェイスホルダー
	private Camera cam; // カメラ
	private Context mContext; // コンテキスト

	// コンストラクタ
	public CameraView(Context con) {
		super(con);

		// サーフェイスホルダー取得
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

		// プッシュバッッファの指定
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mContext = con;
	}

	// サーフェイス生成イベントの処理
	public void surfaceCreated(SurfaceHolder holder) {

		// カメラ設定
		try {
			cam = Camera.open(); // カメラオープン
			cam.setPreviewDisplay(holder);

		} catch (Exception e) {
			Toast.makeText(getContext(), "カメラエラー", Toast.LENGTH_LONG).show();
			cam = null;
		}

	}

	// サーフェイス変更処理
	public void surfaceChanged(SurfaceHolder holder, int fmt, int width,
			int height) {

		cam.startPreview(); // プレビュー開始

	}

	// サーフェイス終了処理
	public void surfaceDestroyed(SurfaceHolder holder) {

		// カメラ停止
		cam.setPreviewCallback(null);
		cam.stopPreview();
		cam.release();
		cam = null;
	}

	// タッチイベント処理
	@Override
	public boolean onTouchEvent(MotionEvent evt) {

		// 動作判定
		if (evt.getAction() == MotionEvent.ACTION_DOWN) {

			// 撮影
			cam.takePicture(null, null, this);
		}
		return true;
	}

	// 撮影メソッド
	public void onPictureTaken(byte[] data, Camera camera) {

		// 撮影データをビットマップに変換
		Bitmap captbmp = BitmapFactory.decodeByteArray(data, 0, data.length);

		// キャンバスに使うBitmapの作成
		Bitmap bmp = Bitmap.createBitmap(captbmp.getWidth(),
				captbmp.getHeight(), Bitmap.Config.ARGB_8888);

		// キャンバス用のビットマップで取得
		Canvas canvas = new Canvas(bmp);

		// キャンバスに撮影ビットマップを描画
		canvas.drawBitmap(captbmp, null, new Rect(0, 0, captbmp.getWidth(),
				captbmp.getHeight()), null);

		// 埋め込み文字の設定
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(ConstDef.IMP_TEXTSIZE);
		// 座標位置の計算
		int Y = canvas.getHeight() - ConstDef.MARGIN_Y; // 描画位置のY座標設定
		int X = ConstDef.MARGIN_X; // 描画位置のX座標設定

		// 埋め込み文字取得
		String str = PreferenceManager.getDefaultSharedPreferences(mContext)
				.getString("KeyImpText", "");
		// 描画
		canvas.drawText(str, X, Y, paint);

		String filePath = Environment.getExternalStorageDirectory() + "/" + str
				+ ".jpg";

		// ＳＤカード保存処理
		try {

			// ファイル出力ストリームの取得
			FileOutputStream filestm = new FileOutputStream(filePath);

			// JPEG圧縮しファイルに保存
			bmp.compress(CompressFormat.JPEG, 100, filestm);

			// ファイルクローズ
			filestm.close();

		} catch (Exception e) {
			Toast.makeText(getContext(), "ＳＤ書き込みエラー", Toast.LENGTH_LONG).show();
		}

		// ギャラリーに登録
		ContentValues cv = new ContentValues();
		ContentResolver cr = mContext.getContentResolver();

		cv.put(Images.Media.MIME_TYPE, "image/jpeg");
		cv.put(Images.Media.TITLE, str);
		cv.put("_data", filePath);
		cr.insert(Media.EXTERNAL_CONTENT_URI, cv);

		// プレビュー再開
		cam.startPreview();
	}

}
