package me.panavtec.drawableview.gestures.scroller;

import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

public class GestureScroller implements GestureScrollListener.OnGestureScrollListener {

  private final ScrollerListener listener;

  private float canvasWidth;
  private float canvasHeight;

  private RectF viewRect = new RectF();
  private RectF canvasRect = new RectF();

  private float scaleFactor = 1.0f;

  public GestureScroller(final ScrollerListener listener) {
    this.listener = listener;
  }

  @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {
    if (hasTwoFingers(e2)) {
      float viewportOffsetX = distanceX * viewRect.width();
      float viewportOffsetY = distanceY * viewRect.height();
      float y = viewRect.bottom + distanceY;
      float x = viewRect.left + distanceX;
      Log.d("onScroll", "Dx: "
          + distanceX
          + ", Dy: "
          + distanceY
          + "VOFX: "
          + viewportOffsetX
          + ", VOFY:"
          + viewportOffsetY
          + ", X: "
          + x
          + ", Y: "
          + y);
      setViewportBottomLeft(x, y);
    }
    return true;
  }

  public void setCanvasBounds(int canvasWidth, int canvasHeight) {
    this.canvasWidth = canvasWidth;
    canvasRect.right = canvasWidth;
    this.canvasHeight = canvasHeight;
    canvasRect.bottom = canvasHeight;
    listener.onCanvasChanged(canvasRect);
  }

  public void setViewBounds(int viewWidth, int viewHeight) {
    viewRect.right = viewWidth;
    viewRect.bottom = viewHeight;
    listener.onViewPortChange(viewRect);
  }

  public void onScaleChange(float scaleFactor) {
    this.scaleFactor = scaleFactor;
    canvasRect.right = canvasWidth * scaleFactor;
    canvasRect.bottom = canvasHeight * scaleFactor;
    listener.onCanvasChanged(canvasRect);
  }

  private void setViewportBottomLeft(float x, float y) {
    float viewWidth = viewRect.width();
    float viewHeight = viewRect.height();
    float left = Math.max(0, Math.min(x, canvasRect.width() - viewWidth));
    float bottom = Math.max(0 + viewHeight, Math.min(y, canvasRect.height()));
    float top = bottom - viewHeight;
    float right = left + viewWidth;
    viewRect.set(left, top, right, bottom);

    listener.onViewPortChange(viewRect);
  }

  private boolean hasTwoFingers(MotionEvent e) {
    return MotionEventCompat.getPointerCount(e) == 2;
  }
}
