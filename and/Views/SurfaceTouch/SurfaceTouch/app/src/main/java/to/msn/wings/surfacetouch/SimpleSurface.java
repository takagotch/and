package to.msn.wings.surfacetouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SimpleSurface extends SurfaceView {
    Path path;
    Paint p;

    public SimpleSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SimpleSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleSurface(Context context) {
        super(context);
        init();
    }

    private void init() {
        p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
        path = new Path();
        getHolder().addCallback(
                new SurfaceHolder.Callback() {
                    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                               int height) {
                    }

                    public void surfaceCreated(SurfaceHolder holder) {
                        draw(holder);
                    }

                    public void surfaceDestroyed(SurfaceHolder holder) {
                    }
                }
        );
    }

    private void draw(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(path, p);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(event.getX(), event.getY());
                break;
        }
        draw(getHolder());
        return true;
    }
}
