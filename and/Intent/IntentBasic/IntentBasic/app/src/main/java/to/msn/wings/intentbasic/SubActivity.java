package to.msn.wings.intentbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Log.d("LIFE", "sub_onCreate");
    }

    public void btnBack_onClick(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.d("LIFE", "sub_onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("LIFE", "sub_onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d("LIFE", "sub_onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("LIFE", "sub_onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d("LIFE", "sub_onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("LIFE", "sub_onStop");
        super.onStop();
    }
}