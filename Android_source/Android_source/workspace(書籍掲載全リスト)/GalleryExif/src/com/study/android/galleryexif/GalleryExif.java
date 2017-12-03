package com.study.android.galleryexif;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

//ギャラリーExif表示アクティビティ
public class GalleryExif extends Activity implements OnItemClickListener {

	// 一覧画像表示サイズ
	private final int SMALL_WIDTH = 100;
	private final int SMALL_HIGHT = 100;

	// フィールド
	private ArrayList<Bitmap> imglist = new ArrayList<Bitmap>();
	private ArrayList<String> filelist = new ArrayList<String>();
	private GridView grview;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイル設定
		setContentView(R.layout.activity_gallery_exif);

		// グリッドビューの取得
		grview = (GridView) findViewById(R.id.gridView1);
		// グリッドビューにアダプターセット
		grview.setAdapter(new GridAdapter());
		// グッリッドビューのクリックリスナーをセット
		grview.setOnItemClickListener(this);

		// カーソルオブジェクトに外部メディアのものを取得
		Cursor cursor = this.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		// カーソルを最初に移動
		cursor.moveToFirst();

		// ビットマップリサイズオブジェクト生成
		BitmapResize brsz = new BitmapResize(this);

		// カーソル数までfor文で実行
		for (int i = 0; i < cursor.getCount(); i++) {

			// ファイルパスを取得
			String fpath = cursor.getString(cursor
					.getColumnIndexOrThrow("_data"));
			// ファイルパスをリストに登録
			filelist.add(fpath);
			// ビットマップリサイズメソッドを呼び出し、画像リストに登録
			imglist.add(brsz.FileResize(fpath, SMALL_WIDTH, SMALL_HIGHT));
			// カーソルを次に移動
			cursor.moveToNext();

		}

	}

	// アイテムクリックアダプターメソッド
	public void onItemClick(AdapterView<?> adpv, View v, int position, long id) {

		// グリッドビューの場合かどうかをチェック
		if (adpv == grview) {

			// DispImageExifアクティビティを設定してインテントを取得
			Intent intent = new Intent(this, DispImageExif.class);
			// ファイルパスを渡す
			intent.putExtra("FILE_PATH", filelist.get(position));
			// アクティビティスタート
			startActivity(intent);
		}
	}

	// グリッドビュー用のアダプタークラス
	public class GridAdapter extends BaseAdapter {

		// カウント取得メソッド
		public int getCount() {

			// 画像リストのサイズを返す
			return imglist.size();
		}

		// アイテム取得メソッド
		public Object getItem(int position) {

			// アイテムの位置を返す
			// return position;
			return imglist.get(position);
		}

		// アイテムID取得メソッド
		public long getItemId(int position) {
			// TODO 自動生成されたメソッド・スタブ
			// return 0;
			return position;
		}

		// ビューの取得メソッド
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView iv;

			// 変換するビューがnullの場合
			if (convertView == null) {

				// イメージビューの取得
				iv = new ImageView(GalleryExif.this);
				// グリッドビューレイアウトパラメータセット
				iv.setLayoutParams(new GridView.LayoutParams(SMALL_WIDTH,
						SMALL_HIGHT));
				// スケールタイプを中央にセット
				iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				// 背景を白にセット
				iv.setBackgroundColor(Color.WHITE);
				// 画像リストからビットマップ取得
				BitmapDrawable bitmapd = new BitmapDrawable(
						imglist.get(position));
				// ビットマップセット
				iv.setImageDrawable(bitmapd);

			} else {
				// 変換するビューのイメージビューを返す
				iv = (ImageView) convertView;
			}

			// イメージビューを返す
			return iv;
		}
	}
}
