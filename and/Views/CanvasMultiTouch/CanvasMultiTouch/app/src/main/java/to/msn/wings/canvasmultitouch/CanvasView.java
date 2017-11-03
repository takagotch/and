package to.msn.wings.canvasmultitouch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
    }

    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            Log.d("MultiTouch",
                    String.format("ID %s > [%s, %s]", event.getPointerId(i),
                            event.getX(i),
                            event.getY(i)
                    )
            );
        }
        return true;
    }
}
