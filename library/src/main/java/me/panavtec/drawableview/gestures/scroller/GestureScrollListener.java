package me.panavtec.drawableview.gestures.scroller;

import android.view.GestureDetector;
import android.view.MotionEvent;

public final class GestureScrollListener implements GestureDetector.OnGestureListener {

  private OnGestureScrollListener listener;

  public GestureScrollListener(OnGestureScrollListener listener) {
    this.listener = listener;
  }

  @Override public boolean onDown(MotionEvent e) {
    return true;
  }

  @Override public void onShowPress(MotionEvent e) {
  }

  @Override public boolean onSingleTapUp(MotionEvent e) {
    return false;
  }

  @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {
    return listener.onScroll(e1, e2, distanceX, distanceY);
  }

  @Override public void onLongPress(MotionEvent e) {
  }

  @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
      float velocityY) {
    return false;
  }

  static interface OnGestureScrollListener {
    boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
  }
}
