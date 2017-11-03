package to.msn.wings.intentobject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SubActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent i = this.getIntent();
        Book b = (Book)i.getSerializableExtra("app");
        Toast.makeText(this,
                b.toString(),
                Toast.LENGTH_LONG).show();
    }

    public void onClick(View v) {
        finish();
    }
}
