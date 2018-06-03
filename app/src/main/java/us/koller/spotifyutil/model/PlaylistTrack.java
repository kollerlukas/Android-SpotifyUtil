package us.koller.spotifyutil.model;

/**
 * POJO for Spotify PlaylistTrack
 * */
@SuppressWarnings("unused")
public class PlaylistTrack {

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
}
