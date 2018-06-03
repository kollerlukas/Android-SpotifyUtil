package us.koller.spotifyutil.spotifyApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import us.koller.spotifyutil.model.PagingObject;
import us.koller.spotifyutil.model.Playlist;
import us.koller.spotifyutil.model.User;
import us.koller.spotifyutil.model.requestBodies.ReorderTrackBody;

/**
 * Interface for Retrofit for the Spotify API
 */
public interface SpotifyService {

    @GET("https://api.spotify.com/v1/me")
    Call<User> fetchUser(@Header("Authorization") String access_token);

    @GET("https://api.spotify.com/v1/users/{user_id}/playlists")
    Call<PagingObject<Playlist>> fetchPlaylists(@Header("Authorization") String access_token,
                                                @Path("user_id") String user_id,
                                                @Query("limit") Integer limit,
                                                @Query("offset") Integer offset);

    @GET("https://api.spotify.com/v1/users/{user_id}/playlists/{playlist_id}")
    Call<Playlist> fetchPlaylist(@Header("Authorization") String access_token,
                                 @Path("user_id") String user_id,
                                 @Path("playlist_id") String playlist_id);

    @PUT("https://api.spotify.com/v1/users/{user_id}/playlists/{playlist_id}/tracks")
    Call<ResponseBody> moveTrack(@Header("Authorization") String access_token,
                                 @Path("user_id") String user_id,
                                 @Path("playlist_id") String playlist_id,
                                 @Body ReorderTrackBody body);
}
