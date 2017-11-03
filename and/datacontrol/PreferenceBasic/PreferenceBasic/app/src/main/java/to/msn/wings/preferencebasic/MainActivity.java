package to.msn.wings.preferencebasic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String msg = "";
        msg += "ユーザー名：" + pref.getString("edittext_name", "ゲスト");
        msg += "\nパスワード：" + pref.getString("edittext_pw", "123abc");
        msg += "\n年齢：" + pref.getString("edittext_age", "20");
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void btn_onClick(View view) {
        Intent i = new Intent(this, MyConfigActivity.class);
        startActivity(i);
    }
}
