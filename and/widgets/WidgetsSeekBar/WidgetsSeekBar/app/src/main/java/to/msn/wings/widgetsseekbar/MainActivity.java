package to.msn.wings.widgetsseekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seek = (SeekBar)findViewById(R.id.seek);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(
                SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(MainActivity.this,
                        String.format("現在値：%d", progress),
                        Toast.LENGTH_SHORT).show();
            }

            // -100～100の範囲で10区切りで変化する場合
            /*public void onProgressChanged(
            SeekBar seekBar, int progress, boolean fromUser) {
                int current = (progress - 10) * 10;
                Toast.makeText(MainActivity.this,
                        String.format("現在値：%d", current),
                        Toast.LENGTH_SHORT).show();
            }*/



            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}
