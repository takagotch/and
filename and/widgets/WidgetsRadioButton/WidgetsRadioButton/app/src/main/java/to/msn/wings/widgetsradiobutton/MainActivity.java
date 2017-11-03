package to.msn.wings.widgetsradiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rgroup = (RadioGroup) findViewById(R.id.rgroup);
        rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = (RadioButton)group.findViewById(checkedId);
                Toast.makeText(MainActivity.this,
                        String.format("「%s」が選択されました。", radio.getText()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
