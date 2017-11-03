package to.msn.wings.dialogcheckbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = {
                "電車", "バス", "徒歩", "マイカー", "自転車", "その他"};
        final boolean[] selected = {true, true, true, false, false, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("通勤手段")
            .setIcon(R.drawable.wings)
            .setMultiChoiceItems(items, selected,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                }
            )
            .setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = "";
                        for (int i = 0; i < selected.length; i++) {
                            if (selected[i]) {
                                msg += items[i] + ",";
                            }
                        }
                        Toast.makeText(getActivity(),
                            String.format("「%s」が選択されました。",
                                msg.substring(0, msg.length() - 1)),
                            Toast.LENGTH_SHORT).show();
                    }
                }
            )
            .create();
    }
}
