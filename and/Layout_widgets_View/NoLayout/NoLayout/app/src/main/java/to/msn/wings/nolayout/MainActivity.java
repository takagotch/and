package to.msn.wings.nolayout;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView txt = new TextView(this);
        RelativeLayout.LayoutParams txtLayout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtLayout.addRule(RelativeLayout.CENTER_HORIZONTAL);
        txtLayout.addRule(RelativeLayout.CENTER_VERTICAL);
        txt.setLayoutParams(txtLayout);
        txt.setText("Hello World!");
        layout.addView(txt);
        setContentView(layout);
    }
}
