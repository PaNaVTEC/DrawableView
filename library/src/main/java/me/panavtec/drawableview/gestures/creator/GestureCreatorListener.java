package me.panavtec.drawableview.gestures.creator;

import me.panavtec.drawableview.draw.SerializablePath;

public interface GestureCreatorListener {
  void onGestureCreated(SerializablePath serializablePath);

  void onCurrentGestureChanged(SerializablePath currentDrawingPath);
}
