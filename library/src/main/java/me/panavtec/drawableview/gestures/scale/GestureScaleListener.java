package me.panavtec.drawableview.gestures.scale;

import android.view.ScaleGestureDetector;

public final class GestureScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
  
  private OnScaleListener listener;

  public GestureScaleListener(OnScaleListener listener) {
    this.listener = listener;
  }

  @Override public boolean onScale(ScaleGestureDetector detector) {
    return listener.onScale(detector.getScaleFactor());
  }

  @Override public boolean onScaleBegin(ScaleGestureDetector detector) {
    return true;
  }

  @Override public void onScaleEnd(ScaleGestureDetector detector) {
  }

  static interface OnScaleListener {
    boolean onScale(float scaleFactor);
  }
}
