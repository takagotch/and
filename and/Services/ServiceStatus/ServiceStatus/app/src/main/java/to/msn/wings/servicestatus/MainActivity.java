package to.msn.wings.servicestatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public final static int ACTIVITY_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartClick(View view) {
        Intent i = new Intent(this, to.msn.wings.servicestatus.SimpleService.class);
        startService(i);
    }

    public void onStopClick(View view) {
        Intent i = new Intent(this,to.msn.wings.servicestatus.SimpleService.class);
        stopService(i);
    }
}
