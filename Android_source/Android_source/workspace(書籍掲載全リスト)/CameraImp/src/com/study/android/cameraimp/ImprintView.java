package com.study.android.cameraimp;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.view.View;

//埋め込み文字ビュー
public class ImprintView extends View {

	// フィールド
	private Context mContext;
	private Paint paint;

	// コンストラクタ
	public ImprintView(Context con) {
		super(con);

		// コンテキストの保存
		mContext = con;
		paint = new Paint();
	}

	// 描画処理
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 埋め込み文字の描画
		paint.setColor(Color.RED);
		paint.setTextSize(ConstDef.IMP_TEXTSIZE);

		// 座標位置の設定
		int X = ConstDef.MARGIN_X;
		int Y = canvas.getHeight() - ConstDef.MARGIN_Y;

		// 埋め込み文字を取得
		String str = PreferenceManager.getDefaultSharedPreferences(mContext)
				.getString("KeyImpText", "");

		// 文字が何も入っていないとき
		if (str == "") {

			// デフォルト文字列を取得
			str = mContext.getString(R.string.ImpText_default);
			// デフォルトプリファレンスを取得
			Editor editor = PreferenceManager.getDefaultSharedPreferences(
					mContext).edit();
			// デフォルト文字をセット
			editor.putString("KeyImpText", str);

		}
		// キャンバスの描画
		canvas.drawText(str, X, Y, paint);
	}
}