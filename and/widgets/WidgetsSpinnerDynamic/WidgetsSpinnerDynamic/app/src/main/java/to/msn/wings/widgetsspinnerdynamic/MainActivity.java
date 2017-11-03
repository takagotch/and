package to.msn.wings.widgetsspinnerdynamic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createSpinner();
    }

    private void createSpinner() {
        ArrayList<String> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
        Calendar cal = Calendar.getInstance();
        for (int i = 1; i < 11; i++) {
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
            list.add(format.format(cal.getTime()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list);
        Spinner spn = (Spinner) findViewById(R.id.spnArch);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner sp = (Spinner) parent;
                Toast.makeText(MainActivity.this,
                        String.format("選択項目：%s", sp.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

