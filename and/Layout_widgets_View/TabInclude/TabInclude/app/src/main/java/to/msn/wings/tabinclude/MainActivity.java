package to.msn.wings.tabinclude;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) this.findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec tab1 = host.newTabSpec("tab1");
        tab1.setIndicator("ホーム");
        tab1.setContent(R.id.tab1);
        host.addTab(tab1);

        TabHost.TabSpec tab2 = host.newTabSpec("tab2");
        tab2.setIndicator("掲示板");
        tab2.setContent(R.id.tab2);
        host.addTab(tab2);

        TabHost.TabSpec tab3 = host.newTabSpec("tab3");
        tab3.setIndicator("ヘルプ");
        tab3.setContent(R.id.tab3);
        host.addTab(tab3);

        host.setCurrentTab(0);
    }
}
