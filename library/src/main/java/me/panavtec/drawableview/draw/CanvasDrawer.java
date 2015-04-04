package me.panavtec.drawableview.draw;

import android.graphics.Canvas;
import android.graphics.RectF;

public class CanvasDrawer {

  private float scaleFactor = 1.0f;
  private RectF viewRect = new RectF();
  
  public void onDraw(Canvas canvas) {
    canvas.scale(scaleFactor, scaleFactor);
    canvas.translate(-viewRect.left, -viewRect.top);
  }

  public void onScaleChange(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }

  public void onViewPortChange(RectF viewRect) {
    this.viewRect = viewRect;
  }
  
}
