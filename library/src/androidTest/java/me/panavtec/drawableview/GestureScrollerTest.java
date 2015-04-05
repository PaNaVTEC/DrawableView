package me.panavtec.drawableview;

import android.graphics.RectF;
import me.panavtec.drawableview.gestures.scroller.GestureScroller;
import me.panavtec.drawableview.gestures.scroller.ScrollerListener;
import org.junit.Before;
import org.junit.Test;

public class GestureScrollerTest implements ScrollerListener {

  private GestureScroller gestureScroller;

  @Before public void setup() {
    gestureScroller = new GestureScroller(this);
    gestureScroller.setCanvasBounds(1000, 1000);
  }

  @Test public void scroll() {
  }

  @Override public void onViewPortChange(RectF currentViewport) {
  }
}
