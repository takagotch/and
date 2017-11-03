package to.msn.wings.widgetsrating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingBar rate = (RatingBar) this.findViewById(R.id.rating);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(MainActivity.this,
                        String.format("現在の評価は%fです。", rating),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
