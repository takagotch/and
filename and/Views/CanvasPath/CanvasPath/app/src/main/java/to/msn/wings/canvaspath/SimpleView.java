package to.msn.wings.canvaspath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {
    private Paint p;
    private Path path;

    public SimpleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public SimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public SimpleView(Context context) {
        super(context);
        initialize();
    }

    public void initialize() {
        p = new Paint();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        p.setColor(Color.CYAN);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        path.moveTo(150, 200);
        path.lineTo(800, 500);
        path.lineTo(200, 150);
        canvas.drawPath(path, p);

        // 平たい角を描画
        /*p.setColor(Color.CYAN);
        p.setStrokeWidth(30);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.BEVEL);
        path.moveTo(210, 300);
        path.lineTo(240, 650);
        path.lineTo(270, 300);
        canvas.drawPath(path, p);

        // 鋭角を描画
        p.setColor(Color.CYAN);
        p.setStrokeWidth(30);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeMiter(30);
        p.setStrokeJoin(Paint.Join.MITER);
        path.moveTo(400, 300);
        path.lineTo(440, 650);
        path.lineTo(470, 300);
        canvas.drawPath(path, p);

        // 丸角を描画
        p.setColor(Color.CYAN);
        p.setStrokeWidth(30);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
        path.moveTo(600, 300);
        path.lineTo(640, 650);
        path.lineTo(670, 300);
        canvas.drawPath(path, p);*/
    }
}
