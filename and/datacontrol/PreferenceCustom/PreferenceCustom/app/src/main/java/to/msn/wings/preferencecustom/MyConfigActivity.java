package to.msn.wings.preferencecustom;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MyConfigActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new MyConfigFragement());
        fragmentTransaction.commit();
    }
}
