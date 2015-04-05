package me.panavtec.drawableview.gestures.scale;

public class GestureScaler implements GestureScaleListener.OnScaleListener {

  private final ScalerListener delegate;
  private float scaleFactor = 1.0f;
  private float minZoom;
  private float maxZoom;

  public GestureScaler(ScalerListener delegate) {
    this.delegate = delegate;
  }

  public void setZooms(float minZoom, float maxZoom) {
    this.minZoom = minZoom;
    this.maxZoom = maxZoom;
  }
  
  @Override public boolean onScale(float deltaScale) {
    scaleFactor *= deltaScale;
    scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom));
    delegate.onScaleChange(scaleFactor);
    return true;
  }
}
