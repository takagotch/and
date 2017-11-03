package to.msn.wings.serviceintent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class SimpleService  extends IntentService {
    private final String TAG = "SimpleService";

    public SimpleService(String name) {
        super(name);
        Log.i(TAG, "constructor");
    }

    public SimpleService() {
        super("SimpleService");
        Log.i(TAG, "constructor");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
        Log.i(TAG, "onHandleIntent");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
