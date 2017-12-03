package com.study.android.cameraimp;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

//埋め込み文字オプション設定
public class SetImpText extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// オプション設定のレイアウトXMLファイルの設定
		addPreferencesFromResource(R.layout.set_imptext);

		// KeyImpTextのキーでプリファレンスデータを取得
		EditTextPreference imptext = (EditTextPreference) getPreferenceScreen()
				.findPreference("KeyImpText");

		// プリファレンス変更リスナーの指定
		imptext.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			// プリファレンス変更メソッド
			public boolean onPreferenceChange(Preference arg0, Object arg1) {

				return true;
			}

		});

	}

}
