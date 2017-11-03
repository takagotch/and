package to.msn.wings.canvastranslate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {
    private Paint p;

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
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        p.setColor(Color.CYAN);
        p.setStrokeWidth(5);
        canvas.translate(200, 200);
        canvas.scale(1.5f, 1.5f);
        canvas.rotate(60);
        canvas.drawRect(0, 0, 200, 200, p);

        //座標軸に傾きを持たせる
        /*canvas.skew(-0.1f, 0.5f);
        canvas.drawRect(200, 200, 500, 500, p);*/

        //座標情報を保存／リストアする
        /*canvas.save();
        canvas.translate(400, 400);
        canvas.drawRect(0, 0, 300, 300, p);
        canvas.restore();
        canvas.drawRect(0, 0, 300, 300, p);*/
    }
}

