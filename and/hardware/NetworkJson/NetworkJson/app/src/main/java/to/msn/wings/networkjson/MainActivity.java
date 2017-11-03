package to.msn.wings.networkjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    AsyncNetworkTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = new AsyncNetworkTask(this);
        task.execute("http://www.wings.msn.to/tmp/it/books.json");
    }

    public void btn_onClick(View view) {
        task.cancel(true);
    }
}
