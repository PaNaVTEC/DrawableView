package me.panavtec.drawableview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import java.util.ArrayList;
import me.panavtec.drawableview.gestures.Drawer;
import me.panavtec.drawableview.gestures.DrawerDelegate;
import me.panavtec.drawableview.gestures.GestureScrollListener;
import me.panavtec.drawableview.gestures.ScaleListener;
import me.panavtec.drawableview.gestures.Scaler;
import me.panavtec.drawableview.gestures.ScalerDelegate;
import me.panavtec.drawableview.gestures.Scroller;
import me.panavtec.drawableview.gestures.ScrollerDelegate;
import me.panavtec.drawableview.internal.DrawableViewSaveState;
import me.panavtec.drawableview.internal.SerializablePath;

public class DrawableView extends View
    implements View.OnTouchListener, ScrollerDelegate, DrawerDelegate, ScalerDelegate {

  private final ArrayList<SerializablePath> historyPaths = new ArrayList<>();

  private Scroller scroller;
  private Scaler scaler;
  private Drawer gestureDrawer;
  private int canvasHeight;
  private int canvasWidth;
  private GestureDetector gestureDetector;
  private ScaleGestureDetector scaleGestureDetector;

  public DrawableView(Context context) {
    super(context);
    init();
  }

  public DrawableView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public DrawableView(Context context, AttributeSet attrs,
      int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    this.scroller = new Scroller(this);
    this.gestureDetector = new GestureDetector(getContext(), new GestureScrollListener(scroller));
    this.scaler = new Scaler(this);
    this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener(scaler));
    this.gestureDrawer = new Drawer(this);
    setOnTouchListener(this);
  }

  public void setConfig(DrawableViewConfig config) {
    if (config == null) {
      throw new RuntimeException("Paint configuration cannot be null");
    }
    canvasWidth = config.getCanvasWidth();
    canvasHeight = config.getCanvasHeight();
    gestureDrawer.setConfig(config);
    scaler.setZooms(config.getMinZoom(), config.getMaxZoom());
    scroller.setCanvasBounds(canvasWidth, canvasHeight);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
    int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
    scroller.setViewBounds(viewWidth, viewHeight);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    scaleGestureDetector.onTouchEvent(event);
    gestureDetector.onTouchEvent(event);
    gestureDrawer.onTouchEvent(event);
    invalidate();
    return true;
  }

  public void undo() {
    if (historyPaths.size() > 0) {
      historyPaths.remove(historyPaths.size() - 1);
      invalidate();
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.save();
    scaler.onDraw(canvas);
    scroller.onDraw(canvas);
    drawGestures(canvas);
    canvas.restore();
  }

  private void drawGestures(Canvas canvas) {
    gestureDrawer.drawGestures(canvas, historyPaths);
    gestureDrawer.onDraw(canvas);
  }

  public void clear() {
    historyPaths.clear();
    invalidate();
  }

  public Bitmap obtainBitmap() {
    Bitmap bmp = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
    Canvas composeCanvas = new Canvas(bmp);
    drawGestures(composeCanvas);
    return bmp;
  }

  @Override protected Parcelable onSaveInstanceState() {
    return new DrawableViewSaveState(super.onSaveInstanceState(), historyPaths);
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof DrawableViewSaveState) {
      DrawableViewSaveState saveState = (DrawableViewSaveState) state;
      super.onRestoreInstanceState(saveState.getSuperState());

      ArrayList<SerializablePath> savedPaths = saveState.getHistoryPaths();
      for (SerializablePath p : savedPaths) {
        p.loadPathPointsAsQuadTo();
      }
      this.historyPaths.addAll(savedPaths);
    } else {
      super.onRestoreInstanceState(state);
    }
  }

  @Override public void onViewPortChange(RectF currentViewport) {
    gestureDrawer.changedViewPort(currentViewport);
  }

  @Override public void onGestureDrawedOk(SerializablePath serializablePath) {
    historyPaths.add(serializablePath);
  }

  @Override public void onScaleChange(float scaleFactor) {
    scroller.onScaleChange(scaleFactor);
    gestureDrawer.onScaleChange(scaleFactor);
  }
}
