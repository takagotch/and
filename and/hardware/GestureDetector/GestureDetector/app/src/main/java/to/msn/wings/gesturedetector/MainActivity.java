package to.msn.wings.gesturedetector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gd = new GestureDetector(this,
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d("Gesture", "DoubleTap");
                    return true;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    Log.d("Gesture", "DoubleTapEvent");
                    return super.onDoubleTapEvent(e);
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    Log.d("Gesture", "Down");
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2,
                   float velocityX, float velocityY) {
                    Log.d("Gesture", "Fling");
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.d("Gesture", "LongPress");
                    super.onLongPress(e);
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2,
                    float distanceX, float distanceY) {
                    Log.d("Gesture", "Scroll");
                    return true;
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    Log.d("Gesture", "ShowPress");
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    Log.d("Gesture", "SingleTapConfirmed");
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("Gesture", "SingleTapUp");
                    return true;
                }
            }
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return true;
    }
}
