package com.study.android.dialogsample;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

// ダイアログサンプル
public class DialogSample extends Activity {

	// フィールド
	private ProgressDialog pdialog;
	int itemSel = 0;
	final String[] itemType = { "１番目", "２番目", "３番目" };
	Thread thread;

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルセット
		setContentView(R.layout.activity_dialog_sample);

	}

	// Dispボタンクリック時のメソッド
	public void onDisp(View v) {

		// スピナーを取得
		Spinner dsel = (Spinner) findViewById(R.id.spinner1);
		// 選択位置の取得
		int sel = dsel.getSelectedItemPosition();

		// アラート+ボタンか？
		if (sel == 0) {

			// アラートダイアログの生成
			AlertDialog.Builder adialog = new AlertDialog.Builder(this);
			// ダイアログタイトルのセット
			adialog.setTitle("アラートダイアログ");
			// 説明メッセージのセット
			adialog.setMessage("OK：ボタン押下");
			// ダイアログにボタンセット
			adialog.setPositiveButton("OK",
			// クリックリスナーの生成と実装
					new DialogInterface.OnClickListener() {

						// onClick()メソッド
						public void onClick(DialogInterface arg0, int arg1) {
							Toast.makeText(DialogSample.this, "ボタンが押されました",
									Toast.LENGTH_LONG).show();

						}
					});
			// ダイアログの表示
			adialog.show();

		}
		// アラート+エディットテキストか？
		else if (sel == 1) {
			// エディットテキストの生成
			final EditText edtxt = new EditText(this);

			// アラートダイアログの生成
			AlertDialog.Builder adialog = new AlertDialog.Builder(this);
			// ダイアログタイトルのセット
			adialog.setTitle("アラートダイアログ");
			// 説明メッセージのセット
			adialog.setMessage("文字を入力してください");
			// ダイアログにエディットテキストをセット
			adialog.setView(edtxt);
			// ダイアログにボタンをセット
			adialog.setPositiveButton("OK",
			// クリックリスナーの生成と実装
					new DialogInterface.OnClickListener() {

						// onClick()メソッド
						public void onClick(DialogInterface arg0, int arg1) {
							Toast.makeText(DialogSample.this,
									edtxt.getText().toString() + "が入力されました",
									Toast.LENGTH_LONG).show();
						}
					});
			// ダイアログの表示
			adialog.show();

		}
		// アラート+アイテム選択か？
		else if (sel == 2) {

			// アラートダイアログの生成
			AlertDialog.Builder adialog = new AlertDialog.Builder(this);
			// ダイアログタイトルのセット
			adialog.setTitle("アラートダイアログ");
			// ダイアログにアイテム選択をセット
			adialog.setSingleChoiceItems(itemType, 0,
			// クリックリスナーの生成と実装
					new DialogInterface.OnClickListener() {

						// onClick()メソッド
						public void onClick(DialogInterface arg0, int arg1) {
							itemSel = arg1;
						}
					});
			// ダイアログにボタンをセット
			adialog.setPositiveButton("OK",
			// クリックリスナーの生成と実装
					new DialogInterface.OnClickListener() {

						// onClick()メソッド
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(DialogSample.this,
									String.valueOf(itemSel + 1) + "番目が選択されました",
									Toast.LENGTH_LONG).show();

						}
					});
			// ダイアログの表示
			adialog.show();

		}
		// デートピッカーか？
		else if (sel == 3) {

			// カレンダーの取得
			Calendar cal = Calendar.getInstance();

			// デートピッカーダイアログの生成
			DatePickerDialog ddialog = new DatePickerDialog(this,
			// デートセットリスナーの生成と実装
					new DatePickerDialog.OnDateSetListener() {

						// onDateSet()メソッド
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							Toast.makeText(
									DialogSample.this,
									String.valueOf(year) + "年"
											+ String.valueOf(monthOfYear + 1)
											+ "月" + String.valueOf(dayOfMonth)
											+ "日" + "が設定されました",
									Toast.LENGTH_LONG).show();

						}

					},
					// カレンダーの日付をセット
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			// ダイアログの表示
			ddialog.show();

		}
		// プログレス+スピンか？
		else if (sel == 4) {

			// プログレスダイアログの生成
			pdialog = new ProgressDialog(this);
			// ダイアログタイトルのセット
			pdialog.setTitle("通信中");
			// 説明メッセージのセット
			pdialog.setMessage("しばらくお待ちください");
			// 進行スタイルにスピンをセット
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// ダイアログの表示
			pdialog.show();

			// スレッドを生成
			thread = new Thread(new Runnable() {

				// 実行内容
				public void run() {

					try {
						// ５秒おやすみ
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
					// ダイアログを消す
					pdialog.dismiss();
				}
			});
			// スレッドスタート
			thread.start();

		}
		// プログレス+ホリゾンタルか？
		else if (sel == 5) {

			// プログレスダイアログの生成
			pdialog = new ProgressDialog(this);
			// ダイアログタイトルのセット
			pdialog.setTitle("通信中");
			// 説明メッセージのセット
			pdialog.setMessage("しばらくお待ちください");
			// 進行スタイルに水平をセット
			pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// ダイアログの表示
			pdialog.show();

			// スレッドを生成
			thread = new Thread(new Runnable() {

				// 実行内容
				public void run() {

					try {

						// 進行ダイアログの最大レベルをセット
						pdialog.setMax(5);

						// ５回繰り返す
						for (int i = 0; i <= 5; i++) {
							// 進行レベルをセット
							pdialog.setProgress(i);
							// スレッド１秒おやすみ
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
					}
					// ダイアログを消す
					pdialog.dismiss();
				}
			});
			// スレッドスタート
			thread.start();
		}

	}

	//終了処理
	@Override
	protected void onDestroy() {
		super.onDestroy();
		thread = null;
	}

}
