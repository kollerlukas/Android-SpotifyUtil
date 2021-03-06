package us.koller.spotifyutil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.koller.spotifyutil.model.Playlist;
import us.koller.spotifyutil.model.PlaylistTrack;
import us.koller.spotifyutil.model.Track;
import us.koller.spotifyutil.model.requestBodies.ReorderTrackBody;
import us.koller.spotifyutil.spotifyApi.SpotifyApiController;

public class PlaylistActivity extends AppCompatActivity implements TrackAdapter.OnTrackMovedCallback {

    public static final String API_CONTROLLER = "koller.API_CONTROLLER";
    public static final String PLAYLIST = "koller.PLAYLIST";
    public static final String TRACKS = "koller.TRACKS";

    public static final String PLAYLIST_ID = "koller.PLAYLIST_ID";
    public static final String SPOTIFY_API_CONTROLLER = "koller.SPOTIFY_API_CONTROLLER";

    private SpotifyApiController controller;

    private TrackAdapter adapter;

    private Playlist playlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //enable the back arrow in the ActionBar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // init recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrackAdapter();
        adapter.setCallback(this);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            controller = intent.getParcelableExtra(SPOTIFY_API_CONTROLLER);
            playlist = intent.getParcelableExtra(PLAYLIST);
            if (playlist == null) {
                // fetch the playlist
                final String playlistId = intent.getStringExtra(PLAYLIST_ID);
                if (controller == null || playlistId == null) {
                    // Something wrong here
                    return;
                }
                controller.fetchPlaylist(playlistId, this::onPlaylistLoaded);
            } else {
                // playlist already provided
                onPlaylistLoaded(playlist);
            }
        } else {
            controller = savedInstanceState.getParcelable(API_CONTROLLER);
            playlist = savedInstanceState.getParcelable(PLAYLIST);
            onPlaylistLoaded(playlist);
        }
    }

    private void onPlaylistLoaded(Playlist playlist) {
        if (playlist == null) {
            return;
        }

        this.playlist = playlist;
        // create a list of tracks
        List<Track> tracks = playlist.getTracks().getItems().stream()
                .map(PlaylistTrack::getTrack).collect(Collectors.toList());
        // change adapter date
        adapter.setTracks(tracks);
        // notify the adapter
        adapter.notifyDataSetChanged();
        //update the actionBar title
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(playlist.getName());
        }
    }

    @Override
    public void onTrackMoved(int from, int to) {
        // create POJO for the body
        ReorderTrackBody reorderTrackBody = new ReorderTrackBody();
        reorderTrackBody.setRange_start(from);
        reorderTrackBody.setRange_length(1);
        reorderTrackBody.setInsert_before(from < to ? to + 1 : to);
        // call Api
        controller.moveTrack(playlist.getId(), reorderTrackBody);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(API_CONTROLLER, controller);
        outState.putParcelable(PLAYLIST, playlist);
        outState.putParcelableArrayList(TRACKS, new ArrayList<>(adapter.getItems()));
    }
}