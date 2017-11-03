package to.msn.wings.dialogbasic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String txtName = getArguments().getString("txtName");
        return builder.setTitle("ダイアログの基本")
                //return builder.setTitle(R.string.d_title)
                //.setMessage("こんにちは、世界！")
                .setMessage(String.format("こんにちは、%sさん！", txtName))
                .setIcon(R.drawable.wings)
                .create();
    }
}
