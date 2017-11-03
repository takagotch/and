package to.msn.wings.dialogbasic;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_onClick(View view) {
        EditText txtName = (EditText)findViewById(R.id.txtName);

        DialogFragment dialog = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString("txtName", txtName.getText().toString());
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "dialog_basic");
    }
}
