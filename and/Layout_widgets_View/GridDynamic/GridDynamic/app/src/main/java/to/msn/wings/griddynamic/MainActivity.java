package to.msn.wings.griddynamic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = {"い", "ろ", "は", "に", "ほ", "へ", "と", "ち", "り", "ぬ", "る", "を" };
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        for (String sData : data) {
            Button btn = new Button(this);
            btn.setText(sData);
            grid.addView(btn);
        }
    }
}
