package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for Spotify PlaylistTrack
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PlaylistTrack implements Parcelable {

    private String added_at;
    private User added_by;
    private boolean is_local;
    private Track track;

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public User getAdded_by() {
        return added_by;
    }

    public void setAdded_by(User added_by) {
        this.added_by = added_by;
    }

    public boolean isIs_local() {
        return is_local;
    }

    public void setIs_local(boolean is_local) {
        this.is_local = is_local;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    // Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(added_at);
        parcel.writeParcelable(added_by, 0);
        parcel.writeInt(is_local ? 1 : 0);
        parcel.writeParcelable(track, 0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PlaylistTrack createFromParcel(Parcel parcel) {
            PlaylistTrack playlistTrack = new PlaylistTrack();
            playlistTrack.setAdded_at(parcel.readString());
            playlistTrack.setAdded_by(parcel.readParcelable(User.class.getClassLoader()));
            playlistTrack.setIs_local(parcel.readInt() == 1);
            playlistTrack.setTrack(parcel.readParcelable(Track.class.getClassLoader()));
            return playlistTrack;
        }

        @Override
        public PlaylistTrack[] newArray(int i) {
            return new PlaylistTrack[i];
        }
    };
}
