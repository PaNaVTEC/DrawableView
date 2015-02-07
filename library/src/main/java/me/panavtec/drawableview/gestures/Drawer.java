package me.panavtec.drawableview.gestures;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import me.panavtec.drawableview.DrawableViewConfig;
import me.panavtec.drawableview.internal.SerializablePath;

import java.util.List;

public class Drawer {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);

    private SerializablePath currentDrawingPath = new SerializablePath();
    private DrawerDelegate delegate;
    private DrawableViewConfig config;
    private boolean downAndUpGesture = false;
    private float scaleFactor = 1.0f;
    private RectF currentViewport = new RectF();

    public Drawer(DrawerDelegate delegate) {
        this.delegate = delegate;

        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void onTouchEvent(MotionEvent event) {
        float touchX = MotionEventCompat.getX(event, 0) / scaleFactor + currentViewport.left;
        float touchY = MotionEventCompat.getY(event, 0) / scaleFactor + currentViewport.top;

        Log.d("Drawer", "T[" + touchX + "," + touchY + "] V[" + currentViewport.toShortString() + "] S[" + scaleFactor + "]");
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                actionDown(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                actionPointerDown();
                break;
        }
    }

    private void actionDown(float touchX, float touchY) {
        if (checkInsideCanvas(touchX, touchY)) {
            downAndUpGesture = true;
            currentDrawingPath = new SerializablePath();
            if (config != null) {
                currentDrawingPath.setColor(config.getStrokeColor());
                currentDrawingPath.setWidth(config.getStrokeWidth());
            }
            currentDrawingPath.saveMoveTo(touchX, touchY);
        }
    }

    private void actionMove(float touchX, float touchY) {
        if (checkInsideCanvas(touchX, touchY)) {
            downAndUpGesture = false;
            if (currentDrawingPath != null) {
                currentDrawingPath.saveLineTo(touchX, touchY);
            }
        }
    }

    private void actionUp() {
        if (currentDrawingPath != null) {
            if (downAndUpGesture) {
                currentDrawingPath.savePoint();
                downAndUpGesture = false;
            }
            delegate.onGestureDrawedOk(currentDrawingPath);
            currentDrawingPath = null;
        }
    }

    private void actionPointerDown() {
        currentDrawingPath = null;
    }

    private boolean checkInsideCanvas(float touchX, float touchY) {
        Log.d("GestureDrawer", "contains: " + currentViewport.toString() + ", X:" + touchX + ",Y:" + touchY);
        return currentViewport.contains(touchX, touchY);
    }

    public void onDraw(Canvas canvas) {
        if (currentDrawingPath != null) {
            drawGesture(canvas, currentDrawingPath);
        }
    }

    public void drawGestures(Canvas canvas, List<SerializablePath> paths) {
        for (SerializablePath path : paths) {
            drawGesture(canvas, path);
        }
    }

    private void drawGesture(Canvas canvas, SerializablePath path) {
        paint.setStrokeWidth(path.getWidth());
        paint.setColor(path.getColor());
        canvas.drawPath(path, paint);
    }

    public void setConfig(DrawableViewConfig config) {
        this.config = config;
    }

    public void onScaleChange(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public void changedViewPort(RectF currentViewport) {
        this.currentViewport = currentViewport;
    }
}
