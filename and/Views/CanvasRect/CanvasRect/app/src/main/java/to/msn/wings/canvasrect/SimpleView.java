package to.msn.wings.canvasrect;

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
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(100, 100, 400, 400, p);

        // setStyleの例
        /*p.setStrokeWidth(50);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 300, 300, p);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(400, 100, 600, 300, p);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(700, 100, 900, 300, p);*/
    }
}

