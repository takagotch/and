package to.msn.wings.viewflippercustom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper vf;
    private float old_x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vf = (ViewFlipper) findViewById(R.id.vf);
        vf.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        old_x = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float new_x = event.getX();
                        if (old_x < new_x) {
                            vf.showNext();
                        }
                        break;
                }
                return true;
            }
        });
    }
}
