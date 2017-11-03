package to.msn.wings.preferencebasic;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class MyConfigFragement extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
