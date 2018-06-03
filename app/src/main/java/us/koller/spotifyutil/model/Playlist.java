package us.koller.spotifyutil.model;

/**
 * POJO for a Spotify Playlist
 * */
@SuppressWarnings("unused")
public class Playlist {

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
}
