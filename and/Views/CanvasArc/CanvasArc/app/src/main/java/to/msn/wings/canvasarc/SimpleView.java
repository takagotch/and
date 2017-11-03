package to.msn.wings.canvasarc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {

    public SimpleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(new RectF(300, 200, 800, 700),
                90, 150, true, p);
    }
}

