package to.msn.wings.networkjson;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncNetworkTask extends AsyncTask<String, Integer, String> {
    private TextView txtResult;
    private ProgressBar progress;

    public AsyncNetworkTask(Context context) {
        super();
        MainActivity activity = (MainActivity)context;
        txtResult = (TextView)activity.findViewById(R.id.txtResult);
        progress = (ProgressBar)activity.findViewById(R.id.progress);
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(30);
        SystemClock.sleep(3000);
        publishProgress(60);
        StringBuilder builder = new StringBuilder();
        StringBuilder list = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "Shift-JIS"));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            try {
                JSONObject json = new JSONObject(builder.toString());
                JSONArray books = json.getJSONArray("books");
                for (int i = 0; i < books.length(); i++) {
                    JSONObject book = books.getJSONObject(i);
                    list.append(book.getString("title")).append("／");
                    list.append(book.getString("price")).append("円\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        publishProgress(100);
        return list.toString();
    }

    @Override
    protected void onPreExecute() {
        progress.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progress.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        txtResult.setText(result);
        progress.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onCancelled() {
        txtResult.setText("キャンセルされました。");
        progress.setVisibility(ProgressBar.GONE);
    }

}
