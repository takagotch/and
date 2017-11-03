package to.msn.wings.propanim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.anim);
        anim.setTarget(btn);
        anim.start();
    }
}
