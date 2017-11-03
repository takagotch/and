package to.msn.wings.camerabasic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurface extends SurfaceView  {
    private Camera c;

    public CameraSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CameraSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraSurface(Context context) {
        super(context);
        init();
    }

    public void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(
                new SurfaceHolder.Callback() {
                    public void surfaceCreated(SurfaceHolder holder) {
                        c = Camera.open(0);
                        try {
                            c.setPreviewDisplay(holder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    public void surfaceChanged(SurfaceHolder holder,
                                               int format, int width, int height) {
                        c.stopPreview();
                        Parameters params = c.getParameters();
          /*params.setPreviewSize(width, height);*/

                        Size sz = params.getSupportedPreviewSizes().get(0);
                        params.setPreviewSize(sz.width, sz.height);

                        c.setParameters(params);
                        c.startPreview();
                    }

                    public void surfaceDestroyed(SurfaceHolder holder) {
                        c.release();
                        c = null;
                    }
                }
        );
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            c.takePicture(
                    new ShutterCallback() {
                        public void onShutter() {}
                    },
                    null, new PictureCallback() {
                        public void onPictureTaken(byte[] data, Camera camera) {
                            try {
                                FileOutputStream fos = new FileOutputStream("/mnt/sdcard/sample.jpg");
                                fos.write(data);
                                fos.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            camera.startPreview();
                        }
                    }
            );
        }
        return true;
    }
}