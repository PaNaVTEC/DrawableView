package me.panavtec.drawableview.draw.log;

import android.graphics.Canvas;
import android.graphics.RectF;

public class NullCanvasLogger implements CanvasLogger {
  @Override public void log(Canvas canvas, RectF canvasRect, RectF viewRect, float scaleFactor) {
  }
}
