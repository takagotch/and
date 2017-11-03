package to.msn.wings.canvaslines;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        p.setStrokeWidth(10);
        float[] ps = { 50, 100, 350, 350, 350, 350,
                575, 100, 575, 100, 720, 350, 720, 350, 900, 100 };
        canvas.drawLines(ps, p);
    }
}

