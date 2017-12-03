package com.study.android.httptimecard;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

//HTTP POSTデータAsyncTask
public class HttpPostData extends AsyncTask<Void, Void, String> {

	// PHPファイルURL
	private final String URL_PHPFILE = "/post_text_data.php";

	// フィールド
	private String filePath;
	private String postUrl;
	private Activity activity;
	private ProgressDialog dialog;
	private Handler handler;

	// コンストラクタ
	public HttpPostData(Activity act, Handler hd, String path) {

		// アクティビティと、ファイルパスを保存
		activity = act;
		filePath = path;
		handler = hd;
	}

	// 前処理
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// エディットテキストを取得
		EditText edit = (EditText) activity.findViewById(R.id.editText1);
		// 送信するURLを得る
		postUrl = edit.getText().toString() + URL_PHPFILE;

		// プログレスダイアログ生成
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

		// POSTメソッドオブジェクト生成
		HttpPost httppost = new HttpPost(postUrl);

		// HTTPクライアントの生成
		HttpClient httpclient = new DefaultHttpClient();

		// マルチパートで送るためのエンティティ生成
		MultipartEntity entity = new MultipartEntity();

		try {

			// ファイルオブジェクト生成
			File file = new File(activity.getFilesDir() + "/" + filePath);

			// ファイル本体とタグをマルチパートのエンティティに追加
			entity.addPart("text_data", new FileBody(file));

			// POSTメソッドにエンティティを設定
			httppost.setEntity(entity);

			// POSTメソッドリクエスト実行->レスポンス取得
			HttpResponse httpres = httpclient.execute(httppost);

			// レスポンスからステータスを取得
			int status = httpres.getStatusLine().getStatusCode();

			// ステータスのチェック
			if (status == HttpStatus.SC_OK) {

				// レスポンスのエンティティの取得
				ret = "成功 "
						+ EntityUtils.toString(httpres.getEntity(), "UTF-8");

			} else {
				Toast.makeText(activity, "Http POST status error ",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			Toast.makeText(activity, "Http POST error ", Toast.LENGTH_LONG)
					.show();
		}

		// HTTP接続シャットダウン
		httpclient.getConnectionManager().shutdown();

		// レスポンスを返す
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
