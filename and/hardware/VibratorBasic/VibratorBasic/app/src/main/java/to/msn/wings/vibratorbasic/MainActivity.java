package to.msn.wings.vibratorbasic;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(100);
        //v.vibrate(new long[] { 1000, 500, 1000, 500, 2000, 500 }, -1);
    }
}
