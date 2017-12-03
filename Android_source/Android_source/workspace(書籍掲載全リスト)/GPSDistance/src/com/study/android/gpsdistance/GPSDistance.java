package com.study.android.gpsdistance;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

//GPS距離計算アクティビティ
public class GPSDistance extends MapActivity implements LocationListener {

	// 日本橋の緯度経度
	private final double LAT_NIHONBASHI = 35.760;
	private final double LNG_NIHONBASHI = 139.775;

	// フィールド
	LocationManager locManager; // ロケーションマネージャー
	private MapView mapView; // マップビュー
	private MapController mapCtrl; // マップコントローラー

	// 起点、終点データ
	private double lat1 = LAT_NIHONBASHI;
	private double lng1 = LNG_NIHONBASHI;
	private double lat2 = LAT_NIHONBASHI;
	private double lng2 = LNG_NIHONBASHI;

	// 起点確定状態　Mapセンター追尾をOFF
	private boolean stpseted = false;

	// アクティビティ生成
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイル設定
		setContentView(R.layout.activity_gpsdistance);

		// マップビューを取得
		mapView = (MapView) findViewById(R.id.mapview);
		// ズームコントローラの設置
		mapView.setBuiltInZoomControls(true);

		// マップコントローラー取得
		mapCtrl = mapView.getController();
		// ズーム指定
		mapCtrl.setZoom(16);

		// ロケーションサービスの取得
		locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		// ロケーション更新のセット：30secごと、10m以上
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000,
				10, this);

		// テキストビューに文字表示
		TextView tv1 = (TextView) findViewById(R.id.text1);
		tv1.setText("起点：");
		TextView tv2 = (TextView) findViewById(R.id.text2);
		tv2.setText("終点：");

		// 中心マークオーバーレイの生成
		CenterMarkOverlay overlay = new CenterMarkOverlay();
		// マップビューのオーバーレイをリスト型で取得
		List<Overlay> overlays = mapView.getOverlays();
		// オーバーレイを追加
		overlays.add(overlay);
		// マップビューの更新
		mapView.invalidate();

	}

	// 停止処理
	@Override
	protected void onStop() {
		super.onStop();

		// ロケーションマネージャ更新の停止
		locManager.removeUpdates(this);
	}

	// 起点ボタン処理
	public void onStartPoint(View view) {

		// マップ上のセンター位置を取得
		GeoPoint geop = mapView.getMapCenter();
		// センターの緯度、経度を取得
		lat1 = geop.getLatitudeE6() / 1E6;
		lng1 = geop.getLongitudeE6() / 1E6;

		// テキストビューに描画
		TextView tv = (TextView) findViewById(R.id.text1);
		tv.setText("起点：" + CalcAddress(lat1, lng1)); // 住所の計算

		// トーストで表示
		Toast.makeText(
				this,
				"＜起点＞\n" + "緯度：" + Double.toString(lat1) + " 経度："
						+ Double.toString(lng1), Toast.LENGTH_LONG).show();

		// 起点メモリー状態：GPS追尾OFF
		stpseted = true;

	}

	// 計算ボタン処理
	public void onCalc(View view) {

		// データの確保
		float[] data = { 0, 0, 0 };

		// マップの中心を取得
		GeoPoint geop = mapView.getMapCenter();
		// 緯度経度を取得
		lat2 = geop.getLatitudeE6() / 1E6;
		lng2 = geop.getLongitudeE6() / 1E6;

		// テキストビューに表示
		TextView tv = (TextView) findViewById(R.id.text2);
		tv.setText("終点：" + CalcAddress(lat2, lng2)); // 住所の計算

		// ２点間の距離を計算
		Location.distanceBetween(lat1, lng1, lat2, lng2, data);

		// トーストで距離、終点の方位、緯度、経度を表示
		Toast.makeText(
				getApplicationContext(),
				"＜終点＞\n" + "距離：" + Double.toString(data[0]) + "\n" + "終点の方位："
						+ Double.toString(data[1]) + "\n" + "緯度："
						+ Double.toString(lat2) + " 経度："
						+ Double.toString(lng2), Toast.LENGTH_LONG).show();
	}

	// クリアボタン処理
	public void onClear(View view) {

		// 最終GPS計測場所の取得
		Location loc = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// 取得できたかのチェック
		if (loc != null) {

			// 最終計測場所の位置を取得
			GeoPoint geop = new GeoPoint((int) (loc.getLatitude() * 1E6),
					(int) (loc.getLongitude() * 1E6));
			// 最終計測場所をマップの中心にする
			mapCtrl.setCenter(geop);
		} else {
			Toast.makeText(getApplicationContext(), "GPS取得エラー",
					Toast.LENGTH_LONG).show();
		}

		// テキストビューをクリア
		TextView tv1 = (TextView) findViewById(R.id.text1);
		tv1.setText("起点：");
		TextView tv2 = (TextView) findViewById(R.id.text2);
		tv2.setText("終点：");

		// 起点状態のクリア：GPS追尾ON
		stpseted = false;
	}

	// データ表示ボタン
	public void onDataDisp(View v) {

		// データの確保
		float[] data = { 0, 0, 0 };

		// ２点間の距離を表示
		Location.distanceBetween(lat1, lng1, lat2, lng2, data);

		// トーストで距離、終点の方位、緯度、経度を表示
		Toast.makeText(
				getApplicationContext(),
				"＜データ＞\n" + "距離：" + Double.toString(data[0]) + "\n" + "終点の方位："
						+ Double.toString(data[1]) + "\n" + "起点緯度："
						+ Double.toString(lat1) + " 起点経度："
						+ Double.toString(lng1) + "\n" + "終点緯度："
						+ Double.toString(lat2) + " 終点経度："
						+ Double.toString(lng2), Toast.LENGTH_LONG).show();

	}

	// GPS位置更新
	public void onLocationChanged(Location location) {

		// 起点確定状態の場合は抜ける
		if (stpseted == true)
			return;

		// 現在位置を取得
		GeoPoint geop = new GeoPoint((int) (location.getLatitude() * 1E6),
				(int) (location.getLongitude() * 1E6));
		// 現在位置をマップの中心にする
		mapCtrl.setCenter(geop);

		// トーストで現在の緯度経度を表示
		Toast.makeText(
				getApplicationContext(),
				"＜現在位置＞\n"
						+ "住所："
						+ CalcAddress(location.getLatitude(),
								location.getLongitude()) + "\n" + "緯度："
						+ Double.toString(lat2) + " 経度："
						+ Double.toString(lng2), Toast.LENGTH_LONG).show();

	}

	// 住所計算
	public String CalcAddress(double lat, double lng) {

		// 文字列バッファーの確保
		StringBuffer strbuf = new StringBuffer();

		try {

			// ジオコーダの取得
			Geocoder gc = new Geocoder(this, Locale.getDefault());

			// 位置情報をリスト形式で取得
			List<Address> address = gc.getFromLocation(lat, lng, 1);

			// Addressオブジェクト確保 addressとなるまで実行
			for (Address ad : address) {
				// インデックスを取得
				int index = ad.getMaxAddressLineIndex();

				// 取得したインデックスまで実施
				for (int i = 1; i <= index; i++) {
					// 文字列バッファに住所をセット
					strbuf.append(ad.getAddressLine(i));
				}
			}

			// 文字列を返す
			return strbuf.toString();

		} catch (IOException e) {

			Toast.makeText(getApplicationContext(), "住所取得できません",
					Toast.LENGTH_LONG).show();
			return "取得できません";
		}

	}

	public void onProviderDisabled(String arg0) {
	}

	public void onProviderEnabled(String arg0) {
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
