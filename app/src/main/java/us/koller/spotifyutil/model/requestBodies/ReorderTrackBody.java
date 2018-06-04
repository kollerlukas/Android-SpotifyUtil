package us.koller.spotifyutil.model.requestBodies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for the PUT-Body of a ReorderTrackBody Operation
 **/
@SuppressWarnings("unused")
public class ReorderTrackBody implements Parcelable {

    private int range_start;
    private int range_length;
    private int insert_before;

    public int getRange_start() {
        return range_start;
    }

    public void setRange_start(int range_start) {
        this.range_start = range_start;
    }

    public int getRange_length() {
        return range_length;
    }

    public void setRange_length(int range_length) {
        this.range_length = range_length;
    }

    public int getInsert_before() {
        return insert_before;
    }

    public void setInsert_before(int insert_before) {
        this.insert_before = insert_before;
    }

    // Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(range_start);
        parcel.writeInt(range_length);
        parcel.writeInt(insert_before);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ReorderTrackBody createFromParcel(Parcel parcel) {
            ReorderTrackBody reorderTrackBody = new ReorderTrackBody();
            reorderTrackBody.setRange_start(parcel.readInt());
            reorderTrackBody.setRange_length(parcel.readInt());
            reorderTrackBody.setInsert_before(parcel.readInt());
            return reorderTrackBody;
        }

        @Override
        public ReorderTrackBody[] newArray(int i) {
            return new ReorderTrackBody[i];
        }
    };
}
