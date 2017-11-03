package to.msn.wings.intentdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSend_onClick(View v) {
        Intent i = new Intent(this, to.msn.wings.intentdata.SubActivity.class);
        EditText txtName = (EditText) findViewById(R.id.txtName);
        i.putExtra("txtName", txtName.getText().toString());
        startActivity(i);
    }
}
