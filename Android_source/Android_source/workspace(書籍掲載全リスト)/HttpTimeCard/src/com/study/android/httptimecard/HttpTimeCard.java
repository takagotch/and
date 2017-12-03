package com.study.android.httptimecard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//HTTPタイムカードアクティビティ
public class HttpTimeCard extends Activity {

	// フィールド
	Calendar calendar;
	private HttpPostData httpPostData;
	private HttpGetData httpGetData;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルの設定
		setContentView(R.layout.activity_http_time_card);
		// カレンダーのインスタンスを生成
		calendar = Calendar.getInstance();

	}

	// サーバー送信ボタン押下処理
	public void onSend(View v) {

		String str = null;

		// 日にち、時間、分を得る
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// ラジオボタンを取得
		RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup1);

		// チェックされているラジオボタンのIDを取得
		int id = radio.getCheckedRadioButtonId();

		// IDがラジオボタン0か？
		if (id == R.id.radio0) {

			// 出社時刻データを作る
			str = String.valueOf(day) + "日," + "出社," + String.valueOf(hour)
					+ ":" + String.valueOf(minute) + ",\n";

			// ファイルに追加書き込み
			FileAppendStore(str);

		}
		// IDがラジオボタン1か？
		else if (id == R.id.radio1) {

			// 退社時刻データを作る
			str = String.valueOf(day) + "日," + "退社," + String.valueOf(hour)
					+ ":" + String.valueOf(minute) + ",\n";

			// ファイルに追加書き込み
			FileAppendStore(str);

		}

		// 現在のファイル名を取得
		String fileName = getNowFileName();

		// HTTP通信AsyncTaskオブジェクト生成（ファイル名を渡す）
		httpPostData = new HttpPostData(this, handler, fileName);

		// AsyncTask実行
		httpPostData.execute();

	}

	// 現在のファイル名を得る
	private String getNowFileName() {

		String fileName = null;

		// 年月を取得
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		// 年月からファイル名を作る
		fileName = "WORK" + String.valueOf(year) + String.valueOf(month)
				+ ".txt";

		// ファイル名を戻す
		return fileName;

	}

	// サーバー読み出しボタン押下時の処理
	public void onRead(View v) {

		String fileName;

		// 現在のファイル名を取得
		fileName = getNowFileName();

		// HTTP GETオブジェクト生成（ファイル名を渡す）
		httpGetData = new HttpGetData(this, handler, fileName);

		// AsyncTask実行
		httpGetData.execute();

	}

	// ハンドラー無名クラス
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			TextView txtview = (TextView) findViewById(R.id.textView1);

			txtview.setText((String) msg.obj);
		}
	};

	// ファイル読み出し処理
	private String FileRead(String fileName) {

		String str = null;
		StringBuffer strbuf = new StringBuffer(100);

		try {
			// Inputファイルストリーム生成
			FileInputStream infile = openFileInput(fileName);

			// 読み出しバッファ生成
			BufferedReader rdbuff = new BufferedReader(new InputStreamReader(
					infile));

			// 1行読みだしてみる
			str = rdbuff.readLine();

			// 空でない場合は可変バッファに追加
			if (str != null)
				strbuf.append(str + "\n");

			// strがnullになるまで実行
			while (str != null) {

				// １行読みだす
				str = rdbuff.readLine();

				// 読み出し終わりでない場合は可変バッファに追加
				if (str != null)
					strbuf.append(str + "\n");

			}

			// クローズ
			rdbuff.close();

			// 読み出しバッファ値を戻す
			return strbuf.toString();

		} catch (Exception e) {
			// エラーの場合はnullを返す
			return null;
		}

	}

	// ファイル書き込み
	private boolean FileWrite(String fileName, String str) {

		try {
			// Outputストリーム生成
			FileOutputStream outfile = openFileOutput(fileName, MODE_PRIVATE);

			// 書き込みバッファ生成
			BufferedWriter wrbuff = new BufferedWriter(new OutputStreamWriter(
					outfile));

			// 書き込み
			wrbuff.write(str);

			// クローズ
			wrbuff.close();

			// 成功を返す
			return true;
		} catch (Exception e) {
			// 失敗を返す
			return false;
		}

	}

	// ファイル追加保存処理
	private void FileAppendStore(String str) {

		String buff = null;
		String fileName;
		StringBuffer strbuf = new StringBuffer(100);

		// 年月を得る
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		// 年月からファイル名を作成
		fileName = "WORK" + String.valueOf(year) + String.valueOf(month)
				+ ".txt";

		// ファイルを読みだす
		buff = FileRead(fileName);

		// ファイルが存在するか？
		if (buff == null) {
			// ファイルが無ければ以下の処理

			// 年月日文字列を得る
			buff = "出勤簿" + String.valueOf(year) + "年" + String.valueOf(month)
					+ "月\n" + str;

			// ファイル書き込み
			if (FileWrite(fileName, buff) == false) {
				// 失敗のトースト表示
				Toast.makeText(HttpTimeCard.this, "ファイル書き込みに失敗しました",
						Toast.LENGTH_SHORT).show();
			}
		}
		// ファイルがある場合
		else {

			// buffにstrを追加
			strbuf.append(buff);
			strbuf.append(str);

			// ファイル書き込み
			if (FileWrite(fileName, strbuf.toString()) == false) {
				Toast.makeText(this, "ファイル書き込みエラー", Toast.LENGTH_LONG).show();
			}
		}

	}

}
