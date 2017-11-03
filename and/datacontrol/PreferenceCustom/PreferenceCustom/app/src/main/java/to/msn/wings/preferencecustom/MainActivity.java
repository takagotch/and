package to.msn.wings.preferencecustom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String msg = "News購読：" + pref.getBoolean("chk", true);

        //String msg = "News購読：" + pref.getBoolean("switch", true);

        //String msg = "血液型：" + pref.getString("list", "A型");

        /*String msg = "OS：";
        Set<String> set = pref.getStringSet("multi", new HashSet<String>());
        for (String str: set) {
            msg += str + " ";
        }*/


        /*String url = pref.getString("sound", "");
        if (url != "") {
            MediaPlayer mp = MediaPlayer.create(this, Uri.parse(url));
            mp.start();
        }*/

        /*String msg = "ユーザー名：" + pref.getString("edittext_name", "なし");
        msg += "\n血液型：" + pref.getString("list", "A型");
        msg += "\nNews購読：" + pref.getBoolean("chk", true);*/

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void btn_onClick(View view) {
        Intent i = new Intent(this, MyConfigActivity.class);
        startActivity(i);
    }
}
