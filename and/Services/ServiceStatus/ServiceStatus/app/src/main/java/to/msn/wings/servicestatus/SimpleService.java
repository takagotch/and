package to.msn.wings.servicestatus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleService extends Service {
    private static final String TAG = "SimpleService";
    private static final int NOTIFY_ID = 0;
    private NotificationManager manager;
    private Timer timer = new Timer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notif = new Notification.Builder(this)
                .setContentTitle("SimpleService")
                .setContentText("サービスは起動中です。")
                .setSmallIcon(R.drawable.wings_logo)
                .setWhen(System.currentTimeMillis())
                .setVibrate(new long[]{1000, 500, 1000, 500, 2000, 500})
                .setContentIntent(
                        PendingIntent.getActivity(this, MainActivity.ACTIVITY_ID,
                                new Intent(this, to.msn.wings.servicestatus.MainActivity.class),
                                PendingIntent.FLAG_CANCEL_CURRENT)
                )
                .build();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFY_ID, notif);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "onStartCommand");
            }
        }, 0, 5000);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.cancel(NOTIFY_ID);
        Log.i(TAG, "onDestroy");
        timer.cancel();
    }
}
