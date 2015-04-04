package me.panavtec.drawableview.gestures.creator;

import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import me.panavtec.drawableview.DrawableViewConfig;
import me.panavtec.drawableview.draw.SerializablePath;

public class GestureCreator {

  private SerializablePath currentDrawingPath = new SerializablePath();
  private GestureCreatorListener delegate;
  private DrawableViewConfig config;
  private boolean downAndUpGesture = false;
  private float scaleFactor = 1.0f;
  private RectF currentViewport = new RectF();

  public GestureCreator(GestureCreatorListener delegate) {
    this.delegate = delegate;
  }

  public void onTouchEvent(MotionEvent event) {
    float touchX = MotionEventCompat.getX(event, 0) / scaleFactor + currentViewport.left;
    float touchY = MotionEventCompat.getY(event, 0) / scaleFactor + currentViewport.top;

    //Log.d("Drawer", "T[" + touchX + "," + touchY + "] V[" + currentViewport.toShortString() + "] S[" + scaleFactor + "]");
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
      delegate.onCurrentGestureChanged(currentDrawingPath);
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
      delegate.onGestureCreated(currentDrawingPath);
      currentDrawingPath = null;
      delegate.onCurrentGestureChanged(null);
    }
  }

  private void actionPointerDown() {
    currentDrawingPath = null;
    delegate.onCurrentGestureChanged(null);
  }

  private boolean checkInsideCanvas(float touchX, float touchY) {
    return currentViewport.contains(touchX, touchY);
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
