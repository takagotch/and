package to.msn.wings.dialoglist;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_onClick(View view) {
        DialogFragment dialog = new MyDialogFragment();
        dialog.show(getFragmentManager(), "dialog_list");
    }
}
