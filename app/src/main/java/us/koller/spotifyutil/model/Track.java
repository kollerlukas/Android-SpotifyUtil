package us.koller.spotifyutil.model;

/**
 * POJO for a Spotify Track
 * */
@SuppressWarnings("unused")
public class Track {

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
}
