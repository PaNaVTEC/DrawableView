package me.panavtec.drawableview.gestures.scroller;

import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class GestureScroller implements GestureScrollListener.OnGestureScrollListener {

  private final ScrollerListener delegate;

  private float canvasWidth;
  private float canvasHeight;

  private RectF viewRect = new RectF();
  private RectF canvasRect = new RectF();

  public GestureScroller(final ScrollerListener delegate) {
    this.delegate = delegate;
  }

  @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {
    if (hasTwoFingers(e2)) {
      float viewportOffsetX = distanceX * viewRect.width() / canvasRect.width();
      float viewportOffsetY = distanceY * viewRect.height() / canvasRect.height();
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
    float currentWidth = viewRect.width();
    float currentHeight = viewRect.height();
    x = Math.max(0, Math.min(x, canvasRect.right - currentWidth));
    y = Math.max(0 + currentHeight, Math.min(y, canvasRect.bottom));

    viewRect.set(x, y - currentHeight, x + currentWidth, y);
    delegate.onViewPortChange(viewRect);
  }

  private boolean hasTwoFingers(MotionEvent e) {
    return MotionEventCompat.getPointerCount(e) == 2;
  }
}
