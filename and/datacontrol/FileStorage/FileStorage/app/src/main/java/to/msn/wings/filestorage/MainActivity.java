package to.msn.wings.filestorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText txtMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMemo = (EditText) findViewById(R.id.txtMemo);

        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TITLE, "memo.txt");
        startActivityForResult(i, 2);
    }

    public void btnSave_onClick(View view) {
        Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TITLE, "memo.txt");
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    getContentResolver().openOutputStream(data.getData())))) {
                writer.write(txtMemo.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            StringBuilder str = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getContentResolver().openInputStream(data.getData())))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                    str.append(System.getProperty("line.separator"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            txtMemo.setText(str.toString());
        }
    }
}
