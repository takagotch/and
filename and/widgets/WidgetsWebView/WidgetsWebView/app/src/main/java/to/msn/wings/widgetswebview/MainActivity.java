package to.msn.wings.widgetswebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_onclick(View view) {
        WebView wv = (WebView) findViewById(R.id.wv);
        switch (view.getId()) {
            case R.id.btnHome:
                wv.loadUrl("http://www.wings.msn.to/");
                break;
            case R.id.btnBbs:
                wv.loadUrl("http://keijiban.msn.to/top.jsp?id=gr7638");
                break;
            case R.id.btnHelp:
                wv.loadUrl("http://www.wings.msn.to/index.php/-/A-08/");
                break;
            default:
                break;
        }
    }
}

