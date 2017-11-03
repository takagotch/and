package to.msn.wings.dialogdate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Locale;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        return new DatePickerDialog(
            getActivity(),
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                    int monthOfYear, int dayOfMonth) {
                    EditText txtDate = (EditText)getActivity().findViewById(R.id.txtDate);
                    txtDate.setText(
                        String.format(Locale.JAPAN, "%02d/%02d/%02d", year, monthOfYear + 1, dayOfMonth));
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
    }
}
