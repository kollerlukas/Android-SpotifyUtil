package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for a Spotify Artist
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Artist implements Parcelable {

    private String href;
    private String id;
    private Image[] images;
    private String name;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(href);
        parcel.writeString(id);
        parcel.writeArray(images);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Artist createFromParcel(Parcel parcel) {
            Artist artist = new Artist();
            artist.setHref(parcel.readString());
            artist.setId(parcel.readString());
            artist.setImages((Image[]) parcel.readArray(Image.class.getClassLoader()));
            artist.setName(parcel.readString());
            return artist;
        }

        @Override
        public Artist[] newArray(int i) {
            return new Artist[i];
        }
    };
}
