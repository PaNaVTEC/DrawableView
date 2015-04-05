package me.panavtec.drawableview.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import me.panavtec.drawableview.BuildConfig;
import me.panavtec.drawableview.DrawableViewConfig;
import me.panavtec.drawableview.draw.log.CanvasLogger;
import me.panavtec.drawableview.draw.log.DebugCanvasLogger;
import me.panavtec.drawableview.draw.log.NullCanvasLogger;

public class CanvasDrawer {

  private boolean showCanvasBounds;
  private Paint paint;
  private float scaleFactor = 1.0f;
  private RectF viewRect = new RectF();
  private RectF canvasRect = new RectF();
  private CanvasLogger canvasLogger;

  public CanvasDrawer() {
    initPaint();
    initLogger();
  }

  public void onDraw(Canvas canvas) {
    canvasLogger.log(canvas, canvasRect, viewRect, scaleFactor);
    if (showCanvasBounds) {
      canvas.drawRect(canvasRect, paint);
    }
    canvas.translate(-viewRect.left, -viewRect.top);
    canvas.scale(scaleFactor, scaleFactor);
  }

  public void onScaleChange(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }

  public void onViewPortChange(RectF viewRect) {
    this.viewRect = viewRect;
  }

  public void onCanvasChanged(RectF canvasRect) {
    this.canvasRect = canvasRect;
  }

  private void initPaint() {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
    paint.setStrokeWidth(2.0f);
    paint.setStyle(Paint.Style.STROKE);
  }

  private void initLogger() {
    if (BuildConfig.DEBUG) {
      canvasLogger = new DebugCanvasLogger();
    } else {
      canvasLogger = new NullCanvasLogger();
    }
  }

  public void setConfig(DrawableViewConfig config) {
    this.showCanvasBounds = config.isShowCanvasBounds();
  }
}
