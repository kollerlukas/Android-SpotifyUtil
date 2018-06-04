package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for a Spotify Playlist
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Playlist implements Parcelable {

    private boolean collaborative;
    private String href;
    private String id;
    private Image[] images;
    private String name;
    private User owner;
    private PagingObject<PlaylistTrack> tracks;

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PagingObject<PlaylistTrack> getTracks() {
        return tracks;
    }

    public void setTracks(PagingObject<PlaylistTrack> tracks) {
        this.tracks = tracks;
    }

    // Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(collaborative ? 1 : 0);
        parcel.writeString(href);
        parcel.writeString(id);
        parcel.writeArray(images);
        parcel.writeString(name);
        parcel.writeParcelable(owner, 0);
        parcel.writeParcelable(tracks, 0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Playlist createFromParcel(Parcel parcel) {
            Playlist playlist = new Playlist();
            playlist.setCollaborative(parcel.readInt() == 1);
            playlist.setHref(parcel.readString());
            playlist.setId(parcel.readString());
            playlist.setImages((Image[]) parcel.readArray(Image.class.getClassLoader()));
            playlist.setName(parcel.readString());
            playlist.setOwner(parcel.readParcelable(User.class.getClassLoader()));
            playlist.setTracks(parcel.readParcelable(PagingObject.class.getClassLoader()));
            return playlist;
        }

        @Override
        public Playlist[] newArray(int i) {
            return new Playlist[i];
        }
    };
}
