package me.panavtec.drawableview.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import me.panavtec.drawableview.BuildConfig;
import me.panavtec.drawableview.draw.log.CanvasLogger;
import me.panavtec.drawableview.draw.log.DebugCanvasLogger;
import me.panavtec.drawableview.draw.log.NullCanvasLogger;

public class CanvasDrawer {

  private Paint paint;
  private float scaleFactor = 1.0f;
  private RectF viewRect = new RectF();
  private RectF canvasRect = new RectF();
  private CanvasLogger canvasLogger;
  private int canvasBackgroundColor;
  private int canvasBorderColor;

  public CanvasDrawer() {
    initPaint();
    initLogger();
  }

  public void onDraw(Canvas canvas) {
    canvasLogger.log(canvas, canvasRect, viewRect, scaleFactor);
    paintCanvas(canvas);
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

  private void paintCanvas(Canvas canvas) {
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(canvasBackgroundColor);
    canvas.drawRect(canvasRect, paint);
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(canvasBorderColor);
    canvas.drawRect(canvasRect, paint);
  }

  private void initPaint() {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
    paint.setStrokeWidth(2.0f);
  }

  private void initLogger() {
    if (BuildConfig.DEBUG) {
      canvasLogger = new DebugCanvasLogger();
    } else {
      canvasLogger = new NullCanvasLogger();
    }
  }

  public void setCanvasBackgroundColor(int canvasBackgroundColor) {
    this.canvasBackgroundColor = canvasBackgroundColor;
  }

  public void setCanvasBorderColor(int canvasBorderColor) {
    this.canvasBorderColor = canvasBorderColor;
  }
}
