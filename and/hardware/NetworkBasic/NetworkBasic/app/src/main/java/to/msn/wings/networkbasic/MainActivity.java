package to.msn.wings.networkbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = new URL("http://www.wings.msn.to/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "Shift-JIS"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            TextView txtResult = (TextView) findViewById(R.id.txtResult);
            txtResult.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
