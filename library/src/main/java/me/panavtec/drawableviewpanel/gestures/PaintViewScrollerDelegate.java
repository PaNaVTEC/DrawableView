package me.panavtec.drawableviewpanel.gestures;

import android.graphics.RectF;

public interface PaintViewScrollerDelegate {
    void onScrollerInvalidate();
    void onViewPortChange(RectF currentViewport);
}
