package me.panavtec.drawableview.gestures;

import android.graphics.RectF;

public interface ScrollerDelegate {
  void onViewPortChange(RectF currentViewport);
}
