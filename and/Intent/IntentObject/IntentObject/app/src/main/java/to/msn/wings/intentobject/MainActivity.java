package to.msn.wings.intentobject;

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

    public void onClick(View v) {
        Intent i = new Intent(this, to.msn.wings.intentobject.SubActivity.class);
        EditText txtTitle = (EditText)this.findViewById(R.id.txtTitle);
        EditText txtPublish = (EditText)this.findViewById(R.id.txtPublish);
        EditText txtPrice = (EditText)this.findViewById(R.id.txtPrice);
        Book b = new Book(
                txtTitle.getText().toString(),
                txtPublish.getText().toString(),
                txtPrice.getText().toString()
        );
        i.putExtra("app", b);
        startActivity(i);
    }
}
