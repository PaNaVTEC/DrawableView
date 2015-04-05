package me.panavtec.drawableview;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import java.util.ArrayList;
import me.panavtec.drawableview.draw.SerializablePath;

public class DrawableViewSaveState extends View.BaseSavedState {

  private ArrayList<SerializablePath> paths;

  public DrawableViewSaveState(Parcel in) {
    super(in);
    this.paths = (ArrayList<SerializablePath>) in.readSerializable();
    for (SerializablePath p : paths) {
      p.loadPathPointsAsQuadTo();
    }
  }

  public DrawableViewSaveState(Parcelable parcelable) {
    super(parcelable);
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeSerializable(this.paths);
  }

  public ArrayList<SerializablePath> getPaths() {
    return paths;
  }

  public void setPaths(ArrayList<SerializablePath> paths) {
    this.paths = paths;
  }

  public static final Creator<DrawableViewSaveState> CREATOR =
      new Creator<DrawableViewSaveState>() {
        public DrawableViewSaveState createFromParcel(Parcel source) {
          return new DrawableViewSaveState(source);
        }

        public DrawableViewSaveState[] newArray(int size) {
          return new DrawableViewSaveState[size];
        }
      };
}
