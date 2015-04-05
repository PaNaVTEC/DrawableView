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
  private RectF viewRect = new RectF();
  private RectF canvasRect = new RectF();

  public GestureCreator(GestureCreatorListener delegate) {
    this.delegate = delegate;
  }

  public void onTouchEvent(MotionEvent event) {
    float touchX = (MotionEventCompat.getX(event, 0) + viewRect.left) / scaleFactor;
    float touchY = (MotionEventCompat.getY(event, 0) + viewRect.top) / scaleFactor;

    //Log.d("Drawer", "T[" + touchX + "," + touchY + "] V[" + viewRect.toShortString() + "] S[" + scaleFactor + "]");
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
    if (insideCanvas(touchX, touchY)) {
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
    if (insideCanvas(touchX, touchY)) {
      downAndUpGesture = false;
      if (currentDrawingPath != null) {
        currentDrawingPath.saveLineTo(touchX, touchY);
      }
    } else {
      actionUp();
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

  private boolean insideCanvas(float touchX, float touchY) {
    return canvasRect.contains(touchX, touchY);
  }

  public void setConfig(DrawableViewConfig config) {
    this.config = config;
  }

  public void onScaleChange(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }

  public void onViewPortChange(RectF viewRect) {
    this.viewRect = viewRect;
  }

  public void onCanvasChanged(RectF canvasRect) {
    this.canvasRect.right = canvasRect.right / scaleFactor;
    this.canvasRect.bottom = canvasRect.bottom / scaleFactor;
  }
}
