package me.panavtec.drawableview.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.List;

public class PathDrawer {

  private Paint gesturePaint;

  public PathDrawer() {
    initGesturePaint();
  }

  public void onDraw(Canvas canvas, SerializablePath currentDrawingPath, List<SerializablePath> paths) {
    drawGestures(canvas, paths);
    if (currentDrawingPath != null) {
      drawGesture(canvas, currentDrawingPath);
    }
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
