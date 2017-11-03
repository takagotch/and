package to.msn.wings.themecode;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.WingsTheme);
        setContentView(R.layout.activity_main);
    }
}
