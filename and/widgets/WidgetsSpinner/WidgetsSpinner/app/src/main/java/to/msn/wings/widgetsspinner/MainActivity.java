package to.msn.wings.widgetsspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner sp = (Spinner) findViewById(R.id.spnOs);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner sp = (Spinner) parent;
                Toast.makeText(MainActivity.this,
                        String.format("選択項目：%s", sp.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}