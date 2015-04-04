package me.panavtec.drawableview.gestures;

import android.graphics.RectF;

public interface ScrollerListener {
  void onViewPortChange(RectF currentViewport);
}
