package to.msn.wings.drawableanimebasic;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.img);
        img.setBackgroundResource(R.drawable.drawable_anim);
        AnimationDrawable anim = (AnimationDrawable) img.getBackground();
        anim.start();
    }
}
