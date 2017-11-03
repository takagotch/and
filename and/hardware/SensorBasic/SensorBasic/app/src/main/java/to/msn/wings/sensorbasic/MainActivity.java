package to.msn.wings.sensorbasic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager manager;
    private SensorEventListener listener;
    private List<Sensor> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        list = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        listener = new SensorEventListener() {
            public void onSensorChanged(SensorEvent event) {
                Toast.makeText(MainActivity.this,
                        "加速度；" + event.values[0], Toast.LENGTH_LONG).show();
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.size() > 0) {
            manager.registerListener(listener, list.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(listener, list.get(0));
    }
}
