package to.msn.wings.surfaceanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SimpleSurface extends SurfaceView {
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
        getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
            }

            public void surfaceCreated(SurfaceHolder holder) {
                draw(holder);
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    private void draw(SurfaceHolder holder) {
        final SurfaceHolder h = holder;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Canvas canvas = h.lockCanvas();
                    canvas.drawColor(Color.WHITE);
                    canvas.drawCircle(100 + i, 100 + i,
                            50, p);
                    h.unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
