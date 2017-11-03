package to.msn.wings.dialogcustom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_body, null);
        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMsg);
        txtMsg.setText(getArguments().getString("txtName"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("ダイアログの基本")
                .setView(layout)
                .setIcon(R.drawable.wings)
                .create();
    }
}
