package me.panavtec.drawableview.gestures;

import me.panavtec.drawableview.internal.SerializablePath;

public interface DrawerListener {
  void onGestureFinished(SerializablePath serializablePath);
}
