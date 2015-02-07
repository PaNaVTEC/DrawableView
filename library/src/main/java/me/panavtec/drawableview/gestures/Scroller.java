package me.panavtec.drawableview.gestures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Scroller implements GestureDetector.OnGestureListener {

    private final ScrollerDelegate delegate;
    private final boolean multiTouch;
    private final GestureDetector gestureDetector;

    private float canvasWidth;
    private float canvasHeight;

    private RectF currentViewport = new RectF();
    private RectF contentRect = new RectF();

    public Scroller(Context context, final ScrollerDelegate delegate, boolean multiTouch) {
        this.delegate = delegate;
        this.multiTouch = multiTouch;
        this.gestureDetector = new GestureDetector(context, this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void onDraw(Canvas canvas) {
        canvas.translate(-currentViewport.left, -currentViewport.top);
    }

    @Override public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override public void onShowPress(MotionEvent e) {
    }

    @Override public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!Scroller.this.multiTouch || MotionEventCompat.getPointerCount(e2) == 2) {
            float viewportOffsetX = distanceX * currentViewport.width() / contentRect.width();
            float viewportOffsetY = distanceY * currentViewport.height() / contentRect.height();

            // Updates the viewport, refreshes the display.
            setViewportBottomLeft(
                    currentViewport.left + viewportOffsetX,
                    currentViewport.bottom + viewportOffsetY);
        }
        return true;
    }

    private void setViewportBottomLeft(float x, float y) {
        float curWidth = currentViewport.width();
        float curHeight = currentViewport.height();
        x = Math.max(0, Math.min(x, contentRect.right - curWidth));
        y = Math.max(0 + curHeight, Math.min(y, contentRect.bottom));

        currentViewport.set(x, y - curHeight, x + curWidth, y);
        delegate.onScrollerInvalidate();
        delegate.onViewPortChange(currentViewport);
    }

    @Override public void onLongPress(MotionEvent e) {
    }

    @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
        contentRect.right = canvasWidth;
    }

    public void setCanvasHeight(int canvasHeight) {
        this.canvasHeight = canvasHeight;
        contentRect.bottom = canvasHeight;
    }
    
    public void setViewBounds(int viewWidth, int viewHeight) {
        currentViewport.right = viewWidth;
        currentViewport.bottom = viewHeight;
        delegate.onViewPortChange(currentViewport);
    }

    public void onScaleChange(float scaleFactor) {
        contentRect.right = canvasWidth * scaleFactor;
        contentRect.bottom = canvasHeight * scaleFactor;
    }
}
