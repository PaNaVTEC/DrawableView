package me.panavtec.drawableview;

import android.graphics.RectF;
import me.panavtec.drawableview.gestures.Scroller;
import me.panavtec.drawableview.gestures.ScrollerListener;
import org.junit.Before;
import org.junit.Test;

public class ScrollerTest implements ScrollerListener {

  private Scroller scroller;

  @Before public void setup() {
    scroller = new Scroller(this);
    scroller.setCanvasBounds(1000, 1000);
  }

  @Test public void scroll() {
  }

  @Override public void onViewPortChange(RectF currentViewport) {
  }
}
