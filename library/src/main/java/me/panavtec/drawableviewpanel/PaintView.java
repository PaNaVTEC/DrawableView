package me.panavtec.drawableviewpanel;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import me.panavtec.drawableviewpanel.gestures.PaintViewDrawerDelegate;
import me.panavtec.drawableviewpanel.gestures.PaintViewGestureDrawer;
import me.panavtec.drawableviewpanel.gestures.PaintViewLogger;
import me.panavtec.drawableviewpanel.gestures.PaintViewScaler;
import me.panavtec.drawableviewpanel.gestures.PaintViewScalerDelegate;
import me.panavtec.drawableviewpanel.gestures.PaintViewScroller;
import me.panavtec.drawableviewpanel.gestures.PaintViewScrollerDelegate;

import java.util.ArrayList;

public class PaintView extends View implements View.OnTouchListener, PaintViewScrollerDelegate, PaintViewDrawerDelegate, PaintViewScalerDelegate {

    private ArrayList<SerializablePath> historyPaths = new ArrayList<>();

    private PaintViewScroller scroller;
    private PaintViewScaler scaler;
    private PaintViewLogger logger;
    private PaintViewGestureDrawer gestureDrawer;
    private int canvasHeight;
    private int canvasWidth;

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public PaintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        scroller = new PaintViewScroller(getContext(), this, true);
        scaler = new PaintViewScaler(getContext(), this);
        logger = new PaintViewLogger();
        gestureDrawer = new PaintViewGestureDrawer(this);
        setOnTouchListener(this);
    }

    public void setConfig(PaintViewConfig config) {
        if (config == null) {
            throw new RuntimeException("Paint configuration cannot be null");
        }

        this.canvasWidth = config.getCanvasWidth();
        this.canvasHeight = config.getCanvasHeight();
        scaler.setMinZoom(config.getMinZoom());
        scaler.setMaxZoom(config.getMaxZoom());
        gestureDrawer.setConfig(config);
        scroller.setCanvasWidth(canvasWidth);
        scroller.setCanvasHeight(canvasHeight);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        scroller.setViewWidth(viewWidth);
        scroller.setViewHeight(viewHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override public boolean onTouch(View v, MotionEvent event) {
        logger.logEvent(event);
        scaler.onTouchEvent(event);
        scroller.onTouchEvent(event);
        gestureDrawer.onTouchEvent(event);
        invalidate();
        return true;
    }

    public void undo() {
        if (historyPaths.size() > 0) {
            historyPaths.remove(historyPaths.size() - 1);
            invalidate();
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        scaler.onDraw(canvas);
        scroller.onDraw(canvas);
        drawGestures(canvas);
        canvas.restore();
    }

    private void drawGestures(Canvas canvas) {
        gestureDrawer.drawGestures(canvas, historyPaths);
        gestureDrawer.onDraw(canvas);
    }

    public void clear() {
        historyPaths.clear();
        invalidate();
    }

    public Bitmap obtainViewDraw() {
        Bitmap bmp = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas composeCanvas = new Canvas(bmp);
        drawGestures(composeCanvas);
        return bmp;
    }

    @Override protected Parcelable onSaveInstanceState() {
        return new PaintViewSaveState(super.onSaveInstanceState(), historyPaths);
    }

    @Override protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof PaintViewSaveState) {
            PaintViewSaveState saveState = (PaintViewSaveState) state;
            super.onRestoreInstanceState(saveState.getSuperState());

            ArrayList<SerializablePath> savedPaths = saveState.getHistoryPaths();
            for (SerializablePath p : savedPaths) {
                p.loadPathPointsAsQuadTo();
            }
            this.historyPaths = savedPaths;

        } else {
            super.onRestoreInstanceState(state);
        }
    }

    @Override public void onScrollerInvalidate() {
        invalidate();
    }

    @Override public void onScrollMove(RectF currentViewport) {
        gestureDrawer.changedViewPort(currentViewport);
    }

    @Override public void onGestureDrawedOk(SerializablePath serializablePath) {
        historyPaths.add(serializablePath);
    }

    @Override public void onScaleChange(float scaleFactor) {
        scroller.onScaleChange(scaleFactor);
        gestureDrawer.onScaleChange(scaleFactor);
    }
}
