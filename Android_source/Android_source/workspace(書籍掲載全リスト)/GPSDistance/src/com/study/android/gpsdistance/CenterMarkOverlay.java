package com.study.android.gpsdistance;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

//センターマークオーバーレイクラス
public class CenterMarkOverlay extends Overlay {

	// 描画
	@Override
	public void draw(Canvas canvas, MapView mapv, boolean shadow) {
		super.draw(canvas, mapv, shadow);

		// ペイント取得
		Paint paint = new Paint();
		// 赤にセット
		paint.setColor(Color.RED);

		// 線の描画用に座標を計算
		float max_x = mapv.getWidth(); // xの終点：マップビューの幅
		float center_x = max_x / 2.0F; // x方向のセンター：終点の半分
		float max_y = mapv.getHeight(); // yの終点：マップビューの高さ
		float center_y = max_y / 2.0F; // y方向のセンター：終点の半分

		// センターマークの描画
		canvas.drawLine(0, center_y, max_x, center_y, paint);
		canvas.drawLine(center_x, 0, center_x, max_y, paint);
	}

}
