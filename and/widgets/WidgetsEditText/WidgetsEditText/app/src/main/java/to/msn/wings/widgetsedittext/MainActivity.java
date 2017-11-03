package to.msn.wings.widgetsedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSend_onClick(View view) {
        EditText txtName = (EditText) findViewById(R.id.txtName);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText(String.format("こんにちは、%sさん！", txtName.getText()));
    }
}
