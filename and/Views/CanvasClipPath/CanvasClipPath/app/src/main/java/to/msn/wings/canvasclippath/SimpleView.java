package to.msn.wings.canvasclippath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {
    private Bitmap bmp;
    private Paint p;
    private Path path;
    private RectF rect;
    /*private RectF rect1;
    private RectF rect2;
    private RectF rect3;*/

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
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sea);
        p = new Paint();
        path = new Path();
        rect = new RectF(100, 100, 1000, 900);

        // 複合的なパス定義
        /*rect1 = new RectF(100, 100, 400, 550);
        rect2 = new RectF(600, 300, 900, 600);
        rect3 = new RectF(100, 500, 1000, 900);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        p.setStrokeWidth(5);
        path.addOval(rect, Path.Direction.CW);

        /*path.addOval(rect1, Path.Direction.CW);
        path.addOval(rect2, Path.Direction.CW);
        path.addRect(rect3, Path.Direction.CW)*/;
        canvas.clipPath(path);
        canvas.drawBitmap(bmp, 0, 0, p);
    }
}
