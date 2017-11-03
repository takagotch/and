package to.msn.wings.widgetscheckbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox chk = (CheckBox)findViewById(R.id.chk);
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Toast.makeText(MainActivity.this,
                        isChecked ? "メール送信をオン" : "メール送信をオフ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
