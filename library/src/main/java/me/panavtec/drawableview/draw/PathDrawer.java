package me.panavtec.drawableview.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.List;

public class PathDrawer {

  private Paint gesturePaint;
  private float scaleFactor = 1.0f;
  private RectF viewRect = new RectF();

  public PathDrawer() {
    initGesturePaint();
  }

  public void onDraw(Canvas canvas, SerializablePath currentDrawingPath, List<SerializablePath> paths) {
    canvas.save();
    canvas.scale(scaleFactor, scaleFactor);
    canvas.translate(-viewRect.left, -viewRect.top);
    drawGestures(canvas, paths);
    if (currentDrawingPath != null) {
      drawGesture(canvas, currentDrawingPath);
    }
    canvas.restore();
  }

  public void drawGestures(Canvas canvas, List<SerializablePath> paths) {
    for (SerializablePath path : paths) {
      drawGesture(canvas, path);
    }
  }

  public Bitmap obtainBitmap(Bitmap createdBitmap, List<SerializablePath> paths) {
    Canvas composeCanvas = new Canvas(createdBitmap);
    drawGestures(composeCanvas, paths);
    return createdBitmap;
  }

  public void onScaleChange(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }

  public void onViewPortChange(RectF viewRect) {
    this.viewRect = viewRect;
  }

  private void drawGesture(Canvas canvas, SerializablePath path) {
    gesturePaint.setStrokeWidth(path.getWidth());
    gesturePaint.setColor(path.getColor());
    canvas.drawPath(path, gesturePaint);
  }

  private void initGesturePaint() {
    gesturePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
    gesturePaint.setStyle(Paint.Style.STROKE);
    gesturePaint.setStrokeJoin(Paint.Join.ROUND);
    gesturePaint.setStrokeCap(Paint.Cap.ROUND);
  }
}
