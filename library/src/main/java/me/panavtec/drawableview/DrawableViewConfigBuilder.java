package me.panavtec.drawableview;

public class DrawableViewConfigBuilder {

  private int canvasWidth;
  private int canvasHeight;
  private float minZoom = 1.0f;
  private float maxZoom = 1.0f;
  private int canvasBorderColor;
  private int canvasBackgroundColor;
  private float strokeWidth;
  private int strokeColor;

  public DrawableViewConfigBuilder(int canvasWidth, int canvasHeight) {
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
  }

  public DrawableViewConfigBuilder minZoom(float minZoom) {
    if (minZoom < 1.0f) {
      throw new IllegalArgumentException("Min Zoom cannot be < 1.0");
    }
    this.minZoom = minZoom;
    return this;
  }

  public DrawableViewConfigBuilder maxZoom(float maxZoom) {
    if (minZoom < 1.0f) {
      throw new IllegalArgumentException("Max Zoom cannot be < 1.0");
    }
    this.maxZoom = maxZoom;
    return this;
  }

  public DrawableViewConfigBuilder canvasBackgroundColor(int backgroundColor) {
    this.canvasBackgroundColor = backgroundColor;
    return this;
  }

  public DrawableViewConfigBuilder canvasBorderColor(int canvasBorderColor) {
    this.canvasBorderColor = canvasBorderColor;
    return this;
  }

  public DrawableViewConfigBuilder strokeWidth(float strokeWidth) {
    if (strokeWidth <= 0.0f) {
      throw new IllegalArgumentException("Stroke width cannot be <= 0.0");
    }
    this.strokeWidth = strokeWidth;
    return this;
  }

  public DrawableViewConfigBuilder strokeColor(int strokeColor) {
    this.strokeColor = strokeColor;
    return this;
  }

  public DrawableViewConfig build() {
    if (minZoom > maxZoom) {
      throw new IllegalArgumentException("Min zoom cannot be > max zoom");
    }
    DrawableViewConfig config = new DrawableViewConfig();
    config.setCanvasWidth(canvasWidth);
    config.setCanvasHeight(canvasHeight);
    config.setMinZoom(minZoom);
    config.setMaxZoom(maxZoom);
    config.setCanvasBackgroundColor(canvasBackgroundColor);
    config.setCanvasBorderColor(canvasBorderColor);
    config.setStrokeWidth(strokeWidth);
    config.setStrokeColor(strokeColor);
    return config;
  }
}
