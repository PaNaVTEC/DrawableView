package me.panavtec.drawableview.gestures;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class Scroller implements GestureScrollListener.OnGestureScrollListener {

  private final ScrollerDelegate delegate;

  private float canvasWidth;
  private float canvasHeight;

  private RectF viewRect = new RectF();
  private RectF canvasRect = new RectF();

  public Scroller(final ScrollerDelegate delegate) {
    this.delegate = delegate;
  }

  public void onDraw(Canvas canvas) {
    canvas.translate(-viewRect.left, -viewRect.top);
  }

  @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {
    if (hasTwoFingers(e2)) {
      float viewportOffsetX = distanceX * viewRect.width() / canvasRect.width();
      float viewportOffsetY = distanceY * viewRect.height() / canvasRect.height();

      // Updates the viewport, refreshes the display.
      setViewportBottomLeft(viewRect.left + viewportOffsetX, viewRect.bottom + viewportOffsetY);
    }
    return true;
  }

  public void setCanvasBounds(int canvasWidth, int canvasHeight) {
    this.canvasWidth = canvasWidth;
    canvasRect.right = canvasWidth;
    this.canvasHeight = canvasHeight;
    canvasRect.bottom = canvasHeight;
  }

  public void setViewBounds(int viewWidth, int viewHeight) {
    viewRect.right = viewWidth;
    viewRect.bottom = viewHeight;
    delegate.onViewPortChange(viewRect);
  }

  public void onScaleChange(float scaleFactor) {
    canvasRect.right = canvasWidth * scaleFactor;
    canvasRect.bottom = canvasHeight * scaleFactor;
  }

  private void setViewportBottomLeft(float x, float y) {
    float curWidth = viewRect.width();
    float curHeight = viewRect.height();
    x = Math.max(0, Math.min(x, canvasRect.right - curWidth));
    y = Math.max(0 + curHeight, Math.min(y, canvasRect.bottom));

    viewRect.set(x, y - curHeight, x + curWidth, y);
    delegate.onViewPortChange(viewRect);
  }

  private boolean hasTwoFingers(MotionEvent e) {
    return MotionEventCompat.getPointerCount(e) == 2;
  }
}
