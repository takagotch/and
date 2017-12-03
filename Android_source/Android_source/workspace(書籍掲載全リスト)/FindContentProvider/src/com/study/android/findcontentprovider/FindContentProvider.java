package com.study.android.findcontentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//コンテントプロバイダ検索アクティビティ
public class FindContentProvider extends Activity {

	// onCreate()メソッド
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// レイアウトファイルセット
		setContentView(R.layout.activity_find_content_provider);
	}

	// 検索ボタン押下時の処理
	public void onSerch(View v) {

		try {
			// 連絡先アプリのURIを指定し、カーソルを取得
			Cursor cursor = getContentResolver().query(
					ContactsContract.Contacts.CONTENT_URI, null, null, null,
					null);

			// カウントが0以外の場合
			if (cursor.getCount() != 0) {

				// 文字列を初期化
				String name = null;
				String dispString = "＜検索結果＞\n";

				// テキストフィールド取得
				EditText editText = (EditText) findViewById(R.id.editText1);
				// テキストフィールドの入力文字列をfindにセット
				String find = editText.getText().toString();

				// カーソルを移動し、最後まで実行
				while (cursor.moveToNext()) {

					// 文字列初期化
					String tel = null;
					String email = null;
					String address = null;
					String findName = null;

					// 連絡先から名前を読み出す
					name = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					// 以下、文字が含まれる場合、代表としてそのカーソル位置の名前を検索結果の名前に入れる

					// 名前に含まれるかのチェック
					if (name.indexOf(find) != -1) {
						// findNameに名前をセット
						findName = name;
					}

					// _IDを読み出す
					String id;
					id = cursor.getString(cursor
							.getColumnIndex(ContactsContract.Contacts._ID));
					String[] selArgs = { id };

					// id、電話URIを指定してカーソルを得る

					Cursor cursorTel = managedQuery(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " =? ", selArgs, null);

					// カーソルを移動し、最後まで実行
					while (cursorTel.moveToNext()) {

						// 電話番号を読み出す
						tel = cursorTel
								.getString(cursorTel
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

						// 電話に含まれるかのチェック
						if (tel.indexOf(find) != -1) {
							// 名前をセット
							findName = name;
							break;
						}
					}

					// クローズ
					cursorTel.close();

					// 電話のid、住所URIを指定してカーソルを得る
					Cursor cursorAddress = managedQuery(
							ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " =? ", selArgs, null);

					// カーソルを移動し、最後まで実行
					while (cursorAddress.moveToNext()) {

						// 住所を読み出す
						address = cursorAddress
								.getString(cursorAddress
										.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA));

						// 住所に含まれるかのチェック
						if (address.indexOf(find) != -1) {
							findName = name;
							break;
						}
					}
					cursorAddress.close();

					// 電話のid、メールURIを指定してカーソルを得る
					Cursor cursorEmail = managedQuery(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " =? ", selArgs, null);

					// カーソルを移動し、最後まで実行
					while (cursorEmail.moveToNext()) {

						// E-mailを読み出す
						email = cursorEmail
								.getString(cursorEmail
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

						// E-mailに含まれるかのチェック
						if (email.indexOf(find) != -1) {
							findName = name;
							break;
						}
					}
					cursorEmail.close();

					// 文字列が含まれるものが見つかったか？
					if (findName != null) {

						// 表示用文字列を作成
						dispString += "名前: " + findName + "\n";

						if (address != null)
							dispString += "住所: " + address + "\n";

						if (tel != null)
							dispString += "電話: " + tel + "\n";

						if (email != null)
							dispString += "E-mail: " + email + "\n";

						dispString += "\n";
					}
				}
				cursor.close();

				// 表示文字列があるか？
				if (dispString != null) {

					// テキストビューに表示
					TextView textView = (TextView) findViewById(R.id.textView1);
					textView.setText(dispString);
				}
			}
		} catch (Exception e) {
			Toast.makeText(this, "連絡先アプリからの読み出しに失敗しました", Toast.LENGTH_LONG)
					.show();
		}

	}

}
