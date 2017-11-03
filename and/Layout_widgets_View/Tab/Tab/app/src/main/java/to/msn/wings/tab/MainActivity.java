package to.msn.wings.tab;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec tab1 = host.newTabSpec("tab1");
        //tab1.setIndicator(getResources().getString(R.string.tab1));
        tab1.setIndicator(null, ContextCompat.getDrawable(this, R.drawable.tab_icon1));
        tab1.setContent(R.id.tab1);
        host.addTab(tab1);

        TabHost.TabSpec tab2 = host.newTabSpec("tab2");
        //tab2.setIndicator(getResources().getString(R.string.tab2));
        tab2.setIndicator(null, ContextCompat.getDrawable(this, R.drawable.tab_icon2));
        tab2.setContent(R.id.tab2);
        host.addTab(tab2);

        TabHost.TabSpec tab3 = host.newTabSpec("tab3");
        //tab3.setIndicator(getResources().getString(R.string.tab3));
        tab3.setIndicator(null, ContextCompat.getDrawable(this, R.drawable.tab_icon3));
        tab3.setContent(R.id.tab3);
        host.addTab(tab3);

        host.setCurrentTab(0);
    }
}
