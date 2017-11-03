package to.msn.wings.propcode;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn);

        ObjectAnimator animX = ObjectAnimator
            .ofFloat(btn, "translationX", 0f, 200f).setDuration(5000);
        ObjectAnimator animY = ObjectAnimator
            .ofFloat(btn, "translationY", 0f, 500f).setDuration(5000);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        ObjectAnimator animAlpha = ObjectAnimator
            .ofFloat(btn, "alpha", 1f, 0f).setDuration(5000);
        AnimatorSet anim = new AnimatorSet();
        anim.playSequentially(animSetXY, animAlpha);
        anim.start();
    }
}
