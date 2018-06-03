package us.koller.spotifyutil.model;

/**
 * POJO for Spotify User
 * */
@SuppressWarnings("unused")
public class User {

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
}
