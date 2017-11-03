package to.msn.wings.intentforresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }

    public void btnBack_onClick(View v) {
        EditText txtName = (EditText) findViewById(R.id.txtName);
        Intent i = new Intent();
        i.putExtra("txtName", txtName.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
}

