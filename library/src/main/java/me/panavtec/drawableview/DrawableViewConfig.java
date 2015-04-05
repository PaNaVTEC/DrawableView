package me.panavtec.drawableview;

import java.io.Serializable;

public class DrawableViewConfig implements Serializable {

  private float strokeWidth;
  private int strokeColor;
  private int canvasWidth;
  private int canvasHeight;
  private float minZoom;
  private float maxZoom;
  private boolean showCanvasBounds;

  public float getMaxZoom() {
    return maxZoom;
  }

  public void setMaxZoom(float maxZoom) {
    this.maxZoom = maxZoom;
  }

  public float getMinZoom() {
    return minZoom;
  }

  public void setMinZoom(float minZoom) {
    this.minZoom = minZoom;
  }

  public int getCanvasHeight() {
    return canvasHeight;
  }

  public void setCanvasHeight(int canvasHeight) {
    this.canvasHeight = canvasHeight;
  }

  public int getCanvasWidth() {
    return canvasWidth;
  }

  public void setCanvasWidth(int canvasWidth) {
    this.canvasWidth = canvasWidth;
  }

  public float getStrokeWidth() {
    return strokeWidth;
  }

  public void setStrokeWidth(float strokeWidth) {
    this.strokeWidth = strokeWidth;
  }

  public int getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(int strokeColor) {
    this.strokeColor = strokeColor;
  }

  public boolean isShowCanvasBounds() {
    return showCanvasBounds;
  }

  public void setShowCanvasBounds(boolean showCanvasBounds) {
    this.showCanvasBounds = showCanvasBounds;
  }
}
