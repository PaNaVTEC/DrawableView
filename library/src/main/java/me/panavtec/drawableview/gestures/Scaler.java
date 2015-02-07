package me.panavtec.drawableview.gestures;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class Scaler implements ScaleGestureDetector.OnScaleGestureListener {

    private final ScalerDelegate delegate;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private float minZoom;
    private float maxZoom;

    public Scaler(Context context, ScalerDelegate delegate) {
        this.delegate = delegate;
        this.scaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    public void onDraw(Canvas canvas) {
        canvas.scale(scaleFactor, scaleFactor);
    }

    public void onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    @Override public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();
        scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom));
        Scaler.this.delegate.onScaleChange(scaleFactor);
        return true;
    }

    @Override public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override public void onScaleEnd(ScaleGestureDetector detector) {
    }

}
