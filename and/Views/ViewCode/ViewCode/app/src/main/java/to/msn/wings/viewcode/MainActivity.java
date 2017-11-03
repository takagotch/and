package to.msn.wings.viewcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.img);

        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        alpha.setRepeatCount(10);
        alpha.setRepeatMode(Animation.REVERSE);

        RotateAnimation rotate = new RotateAnimation(0, 90,
                img.getWidth() / 2, img.getHeight() / 2);
        rotate.setDuration(1000);
        rotate.setRepeatCount(10);
        rotate.setRepeatMode(Animation.REVERSE);

        AnimationSet anim = new AnimationSet(false);
        anim.addAnimation(alpha);
        anim.addAnimation(rotate);

        img.startAnimation(anim);
    }
}
