package jp.co.se.androidkakin.appendix.Ap1_1_AdmobSample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = (AdView)this.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
