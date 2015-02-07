package me.panavtec.drawableview.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

public class DrawableViewSaveState extends View.BaseSavedState {

    private ArrayList<SerializablePath> historyPaths;

    public DrawableViewSaveState(Parcel in) {
        super(in);
        this.historyPaths = (ArrayList<SerializablePath>) in.readSerializable();
    }

    public DrawableViewSaveState(Parcelable parcelable, ArrayList<SerializablePath> historyPaths) {
        super(parcelable);
        this.historyPaths = historyPaths;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.historyPaths);
    }

    public ArrayList<SerializablePath> getHistoryPaths() {
        return historyPaths;
    }

    public static final Creator<DrawableViewSaveState> CREATOR = new Creator<DrawableViewSaveState>() {
        public DrawableViewSaveState createFromParcel(Parcel source) {
            return new DrawableViewSaveState(source);
        }

        public DrawableViewSaveState[] newArray(int size) {
            return new DrawableViewSaveState[size];
        }
    };
}
