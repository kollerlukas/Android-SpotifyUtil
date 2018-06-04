package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for a Spotify Track
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Track implements Parcelable {

    private Artist[] artists;
    private int duration_ms;
    private boolean explicit;
    private String href;
    private String id;
    private String name;

    public Artist[] getArtists() {
        return artists;
    }

    public String getArtistsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : artists) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(artist.getName());
        }
        return stringBuilder.toString();
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

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
        parcel.writeArray(artists);
        parcel.writeInt(duration_ms);
        parcel.writeInt(explicit ? 1 : 0);
        parcel.writeString(href);
        parcel.writeString(id);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Track createFromParcel(Parcel parcel) {
            Track track = new Track();
            track.setArtists((Artist[]) parcel.readArray(Artist.class.getClassLoader()));
            track.setDuration_ms(parcel.readInt());
            track.setExplicit(parcel.readInt() == 1);
            track.setHref(parcel.readString());
            track.setId(parcel.readString());
            track.setName(parcel.readString());
            return track;
        }

        @Override
        public Track[] newArray(int i) {
            return new Track[i];
        }
    };
}
