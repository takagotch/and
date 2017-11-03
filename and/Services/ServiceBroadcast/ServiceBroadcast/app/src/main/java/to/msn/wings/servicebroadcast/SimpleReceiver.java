package to.msn.wings.servicebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimpleReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("message");
        Toast.makeText(context, "現在時刻：" + msg,
                Toast.LENGTH_SHORT).show();
    }
}
