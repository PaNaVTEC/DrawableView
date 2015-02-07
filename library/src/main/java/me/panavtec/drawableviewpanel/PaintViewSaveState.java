package me.panavtec.drawableviewpanel;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

public class PaintViewSaveState extends View.BaseSavedState {

    private ArrayList<SerializablePath> historyPaths;

    public PaintViewSaveState(Parcel in) {
        super(in);
        this.historyPaths = (ArrayList<SerializablePath>) in.readSerializable();
    }

    public PaintViewSaveState(Parcelable parcelable, ArrayList<SerializablePath> historyPaths) {
        super(parcelable);
        this.historyPaths = historyPaths;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.historyPaths);
    }

    public ArrayList<SerializablePath> getHistoryPaths() {
        return historyPaths;
    }

    public static final Creator<PaintViewSaveState> CREATOR = new Creator<PaintViewSaveState>() {
        public PaintViewSaveState createFromParcel(Parcel source) {
            return new PaintViewSaveState(source);
        }

        public PaintViewSaveState[] newArray(int size) {
            return new PaintViewSaveState[size];
        }
    };
}
