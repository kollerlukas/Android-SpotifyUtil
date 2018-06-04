package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for a Image for a Playlist
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Image implements Parcelable {

    private int height;
    private String url;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    // Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(height);
        parcel.writeString(url);
        parcel.writeInt(width);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Image createFromParcel(Parcel parcel) {
            Image image = new Image();
            image.setHeight(parcel.readInt());
            image.setUrl(parcel.readString());
            image.setWidth(parcel.readInt());
            return image;
        }

        @Override
        public Image[] newArray(int i) {
            return new Image[i];
        }
    };
}
