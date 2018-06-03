package us.koller.spotifyutil.spotifyApi;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import us.koller.spotifyutil.model.PagingObject;
import us.koller.spotifyutil.model.Playlist;
import us.koller.spotifyutil.model.User;
import us.koller.spotifyutil.model.requestBodies.ReorderTrackBody;

/**
 * Wrapper class for the Retrofit requests
 */
public class SpotifyApiController implements Parcelable {

    private static final String BASE_URL = "https://api.spotify.com/";

    private String mAccessToken;
    private SpotifyService service;

    private User user;

    public SpotifyApiController(String accessToken) {
        mAccessToken = accessToken;

        // init Retrofit & Gson
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // create an Api Service instance
        service = retrofit.create(SpotifyService.class);
    }

    /**
     * internal method the fetch the current user object
     **/
    private void fetchUser(ApiCallback<User> callback) {
        service.fetchUser(mAccessToken).enqueue(new SimpleCallback<User>() {
            @Override
            public void onResult(User result) {
                super.onResult(result);
                callback.onResult(result);
            }
        });
    }

    /**
     * entry point for an Api caller to fetch all the Playlists associated with the current user
     **/
    public void fetchPlaylists(final ApiCallback<PagingObject<Playlist>> callback) {
        if (user != null) {
            // request
            fetchPlaylists(null, callback);
        } else {
            // User wasn't fetched => fetchUser()
            fetchUser(result -> {
                user = result;
                fetchPlaylists(callback);
            });
        }
    }

    /**
     * recursive method to fetch all playlists
     **/
    private void fetchPlaylists(final PagingObject<Playlist> pagingObject,
                                final ApiCallback<PagingObject<Playlist>> callback) {
        if (user == null) {
            // something wrong here
            return;
        }
        // fetch the playlists
        Integer limit = null, offset = null;
        if (pagingObject != null) {
            limit = pagingObject.getLimit();
            offset = limit + pagingObject.getOffset();
        }
        service.fetchPlaylists(mAccessToken, user.getId(), limit, offset)
                .enqueue(new SimpleCallback<PagingObject<Playlist>>() {
                    @Override
                    public void onResult(PagingObject<Playlist> result) {
                        super.onResult(result);
                        // add other already retrieved playlists to current playlists object
                        if (pagingObject != null) {
                            result.getItems().addAll(0, pagingObject.getItems());
                        }
                        // if there are other pages of playlists => fetch them
                        if (result.getNext() != null) {
                            // next request
                            fetchPlaylists(result, callback);
                        } else {
                            // no more pages
                            callback.onResult(result);
                        }
                    }
                });
    }

    /**
     * entry point for an API caller to fetch the playlist object associated to the given playlist_id
     **/
    public void fetchPlaylist(final String playlist_id, final ApiCallback<Playlist> callback) {
        if (user != null) {
            service.fetchPlaylist(mAccessToken, user.getId(), playlist_id)
                    .enqueue(new SimpleCallback<Playlist>() {
                        @Override
                        public void onResult(Playlist result) {
                            super.onResult(result);
                            callback.onResult(result);
                        }
                    });
        } else {
            // User wasn't fetched => fetchUser()
            fetchUser(result -> {
                user = result;
                fetchPlaylist(playlist_id, callback);
            });
        }
    }

    /**
     * entry point for an API caller to move tracks within a given playlist
     **/
    public void moveTrack(final String playlist_id, final ReorderTrackBody reorderTrackBody) {
        Log.d("SpotifyApiController", "moveTrack() called with: playlist_id = [" + playlist_id + "], reorderTrackBody = [" + reorderTrackBody + "]");
        if (user != null) {
            service.moveTrack(mAccessToken, user.getId(), playlist_id, reorderTrackBody)
                    .enqueue(new SimpleCallback<ResponseBody>() {});
        } else {
            // User wasn't fetched => fetchUser()
            fetchUser(result -> {
                user = result;
                moveTrack(playlist_id, reorderTrackBody);
            });
        }
    }

    public User getUser() {
        return user;
    }

    /**
     * Wrapper class for the Retrofit Callback
     **/
    @SuppressWarnings("WeakerAccess")
    private static abstract class SimpleCallback<T> implements Callback<T> {

        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
            onResult(response.body());
        }

        public void onResult(T result) {

        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            onResult(null);
        }
    }

    /**
     * Result interface for an API caller
     **/
    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface ApiCallback<T> {
        public void onResult(T result);
    }

    // Parceble stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAccessToken);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SpotifyApiController createFromParcel(Parcel parcel) {
            String accessToken = parcel.readString();
            return new SpotifyApiController(accessToken);
        }

        @Override
        public SpotifyApiController[] newArray(int i) {
            return new SpotifyApiController[i];
        }
    };
}