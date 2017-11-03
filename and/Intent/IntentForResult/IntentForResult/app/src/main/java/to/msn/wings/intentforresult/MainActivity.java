package to.msn.wings.intentforresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSend_onClick(View v) {
        Intent i = new Intent(this, to.msn.wings.intentforresult.SubActivity.class);
        this.startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String txtName = data.getStringExtra("txtName");
            Toast.makeText(this,
                String.format("こんにちは、%sさん！", txtName),
                Toast.LENGTH_SHORT).show();
        }
    }
}
