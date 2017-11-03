package to.msn.wings.canvastext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
        p.setTypeface(Typeface.SERIF);
        p.setTextSize(50);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextScaleX(1.5f);
        p.setTextSkewX(-0.5f);
        canvas.drawText("WINGSプロジェクト", 400, 300, p);
    }
}

