package us.koller.spotifyutil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.koller.spotifyutil.model.Playlist;
import us.koller.spotifyutil.model.User;
import us.koller.spotifyutil.spotifyApi.SpotifyApiController;

public class MainActivity extends AppCompatActivity implements PlaylistAdapter.PlaylistClickListener {

    private static final String PLAYLISTS = "koller.PLAYLISTS";
    private static final String API_CONTROLLER = "koller.API_CONTROLLER";

    private static final String CLIENT_ID = "760bffb64af64f7ab95758c76f8d5ad9";
    private static final String REDIRECT_URL = "callback://koller.spotifyutil";
    private static final int AUTH_TOKEN_REQUEST_CODE = 1;

    // define Auth scopes
    private static final String[] AUTH_SCOPES = {
            "playlist-modify-public", // Write access to a user's public playlists.
            "playlist-read-private", // Read access to user's private playlists.
            "playlist-modify-private", // Write access to a user's private playlists.
            "user-read-email",
    };

    private PlaylistAdapter adapter;

    private SpotifyApiController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.playlists);
        }

        // init recyclerView
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlaylistAdapter();
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            // launch Login Activity
            AuthenticationClient.openLoginActivity(this,
                    AUTH_TOKEN_REQUEST_CODE, createAuthRequest());
        } else {
            controller = savedInstanceState.getParcelable(API_CONTROLLER);
            adapter.setPlaylists(savedInstanceState.getParcelableArrayList(PLAYLISTS));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPlaylistClicked(String playlist_id) {
        // launch PlaylistActivity
        Intent intent = new Intent(this, PlaylistActivity.class);
        intent.putExtra(PlaylistActivity.SPOTIFY_API_CONTROLLER, controller);
        intent.putExtra(PlaylistActivity.PLAYLIST_ID, playlist_id);
        startActivity(intent);
    }

    private void fetchPlaylists() {
        // request the users playlists
        controller.fetchPlaylists(result -> {
            User user = controller.getUser();
            // filter playlists if owned by current user
            List<Playlist> playlists = result.getItems()
                    .stream()
                    .filter(p -> user.getId().equals(p.getOwner().getId()))
                    .collect(Collectors.toList());
            // upadate the PlaylistAdapter
            adapter.setPlaylists(playlists);
            adapter.notifyDataSetChanged();
        });
    }

    private AuthenticationRequest createAuthRequest() {
        // create AuthenticationRequest
        return new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URL)
                .setScopes(AUTH_SCOPES)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AUTH_TOKEN_REQUEST_CODE:
                // Result after trying to login
                final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
                switch (response.getType()) {
                    case TOKEN:
                        // Response was successful and contains auth token
                        String accessToken = "Bearer " + response.getAccessToken();
                        // instanciate ApiController
                        controller = new SpotifyApiController(accessToken);
                        // load the users playlists
                        fetchPlaylists();
                        break;
                    case ERROR:
                        // Auth flow returned an error
                        Toast.makeText(this, "Error: " + response.getError(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        // Most likely auth flow was cancelled
                        break;
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(API_CONTROLLER, controller);
        outState.putParcelableArrayList(PLAYLISTS, new ArrayList<>(adapter.getPlaylists()));
    }
}