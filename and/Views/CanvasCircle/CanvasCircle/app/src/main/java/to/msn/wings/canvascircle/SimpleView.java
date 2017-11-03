package to.msn.wings.canvascircle;

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
        p.setStrokeWidth(20);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200, 200, 100, p);
        p.setColor(Color.GREEN);
        canvas.drawOval(new RectF(200, 400, 800, 800), p);
    }
}

