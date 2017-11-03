package to.msn.wings.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
     public void btnCurrent_onClick(View view) {
        TextView txt = (TextView)findViewById(R.id.txtResult);
        txt.setText(new Date().toString());
        Log.d("CurrentTime", new Date().toString());
    }

    // トースト表示
/*    public void btnCurrent_onClick(View view){
        Toast toast = Toast.makeText(
        this, new Date().toString(), Toast.LENGTH_LONG);
        // 固定文字列を指定
        // Toast toast = Toast.makeText(this, R.string.message, Toast.LENGTH_LONG);
        toast.show();


        // Toast.makeText(this, new Date().toString(), Toast.LENGTH_LONG).show();
    }*/

    //  イベントリスナーの利用
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btnCurrent);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txt = (TextView)findViewById(R.id.txtResult);
                        txt.setText(new Date().toString());
                    }
                }
        );
    }*/

    // イベントリスナーでトースト表示
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btnCurrent);
        btn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,
                            new Date().toString(), Toast.LENGTH_LONG).show();
                }
            }
        );
    }*/

    // 匿名クラスを原始的に記述
/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btnCurrent);
        btn.setOnClickListener(new MyListener());
    }

    class MyListener implements View.OnClickListener {
        public void onClick(View v) {
            TextView txt = (TextView)findViewById(R.id.txtResult);
            txt.setText(new Date().toString());
        }
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView txtResult = (TextView)findViewById(R.id.txtResult);
        outState.putString("txtResult", txtResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView txtResult = (TextView)findViewById(R.id.txtResult);
        txtResult.setText(savedInstanceState.getString("txtResult"));
    }
}
