/*
 * Copyright (c) 2014 Selltag. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Selltag ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Selltag.
 */
package me.panavtec.drawableview.gestures;

import android.util.Log;
import android.view.MotionEvent;
import me.panavtec.drawableview.BuildConfig;

public class Logger {

    private static final String TAG = "PaintView";

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }

    public void logEvent(MotionEvent event) {
        if (BuildConfig.DEBUG) {
            dumpEvent(event);
        }
    }

}
