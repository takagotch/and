package to.msn.wings.dialogbutton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("ダイアログの基本")
            .setMessage("AndroidはJavaで開発できますか？")
            .setIcon(R.drawable.wings)
            .setPositiveButton("はい",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "正解です！", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        )
        .setNegativeButton("いいえ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ミス！", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        )
        .setNeutralButton("キャンセル",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                }
            )
        .create();
    }
}
