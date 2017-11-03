package to.msn.wings.dialoglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = { "A型", "B型", "O型", "AB型" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("血液型")
                .setIcon(R.drawable.wings)
                .setItems(items,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(),
                                        String.format("「%s」が選択されました。", items[which]),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .create();

    }
}
