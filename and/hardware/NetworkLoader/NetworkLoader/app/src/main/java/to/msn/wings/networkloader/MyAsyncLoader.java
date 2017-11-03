package to.msn.wings.networkloader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsyncLoader extends AsyncTaskLoader<String> {
    private String url;

    public MyAsyncLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public String loadInBackground() {
        SystemClock.sleep(3000);
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "Shift-JIS"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
