package to.msn.wings.widgetsswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch sw = (Switch) findViewById(R.id.sw);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MainActivity.this,
                        isChecked ? "メール送信をオン" : "メール送信をオフ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
