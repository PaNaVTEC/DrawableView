package me.panavtec.drawableview.gestures;

import me.panavtec.drawableview.internal.SerializablePath;

public interface DrawerDelegate {
    void onGestureDrawedOk(SerializablePath serializablePath);
}
