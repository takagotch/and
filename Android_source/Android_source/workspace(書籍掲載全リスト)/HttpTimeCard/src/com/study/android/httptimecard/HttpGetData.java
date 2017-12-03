package com.study.android.httptimecard;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

//HTTP GETデータAsyncTask
public class HttpGetData extends AsyncTask<Void, Void, String> {

	// フィールド
	private String inputUrl;
	private Activity activity;
	private ProgressDialog dialog;
	private String filePath;
	private Handler handler;

	// コンストラクタ
	public HttpGetData(Activity act, Handler hd, String path) {

		// アクティビティとファイル名の保存
		activity = act;
		filePath = path;
		handler = hd;
	}

	// 前処理
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// エディットテキストからURL文字列を取得
		EditText edit = (EditText) activity.findViewById(R.id.editText1);
		// URLの保存
		inputUrl = edit.getText().toString() + "/files/";

		// ダイアログ生成
		dialog = new ProgressDialog(activity);
		// メッセージ設定
		dialog.setMessage("HTTP通信中");
		// 表示
		dialog.show();
	}

	// バックグラウンド処理
	@Override
	protected String doInBackground(Void... params) {

		String ret = null;

		// GETメソッドオブジェクト生成、URI設定
		HttpGet httpget = new HttpGet(inputUrl + filePath);

		// HTTPクライアント生成
		HttpClient httpclient = new DefaultHttpClient();

		try {
			// GETメソッドリクエスト実行->レスポンス取得
			HttpResponse httpres = httpclient.execute(httpget);

			// レスポンスからステータス取得
			int status = httpres.getStatusLine().getStatusCode();

			// ステータスのチェック
			if (status == HttpStatus.SC_OK) {

				// レスポンスのエンティティの取得
				ret = EntityUtils.toString(httpres.getEntity(), "UTF-8");

			} else {
				Toast.makeText(activity, "Http GET status error ",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(activity, "Http GET error ", Toast.LENGTH_LONG)
					.show();
		}

		// HTTP接続シャットダウン
		httpclient.getConnectionManager().shutdown();

		// エンティティを返す
		return ret;
	}

	// 後処理
	@Override
	protected void onPostExecute(String ret) {
		super.onPostExecute(ret);

		// ダイアログ閉じる
		dialog.dismiss();

		// ハンドラーにメッセージを投げる
		handler.sendMessage(Message.obtain(handler, 0, ret));

	}

}
