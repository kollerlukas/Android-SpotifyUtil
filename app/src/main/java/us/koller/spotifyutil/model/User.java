package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for Spotify User
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public class User implements Parcelable {

    private String display_name;
    private String id;
    private Image[] images;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
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

    // Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(display_name);
        parcel.writeString(id);
        parcel.writeArray(images);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel parcel) {
            User user = new User();
            user.setDisplay_name(parcel.readString());
            user.setId(parcel.readString());
            user.setImages((Image[]) parcel.readArray(Image.class.getClassLoader()));
            return user;
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
}
