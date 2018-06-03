package us.koller.spotifyutil;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import us.koller.spotifyutil.dragRecyclerView.DragRecyclerViewAdapter;
import us.koller.spotifyutil.model.Track;

@SuppressWarnings("WeakerAccess")
public class TrackAdapter extends DragRecyclerViewAdapter<Track, TrackAdapter.TrackViewHolder> {

    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface OnTrackMovedCallback {
        public void onTrackMoved(int from, int to);
    }

    private List<Track> tracks;

    private OnTrackMovedCallback callback;

    public TrackAdapter() {
        tracks = new ArrayList<>();
    }

    @Override
    public List<Track> getItems() {
        return tracks;
    }

    @Override
    public void onItemMoved(int from, int to) {
        callback.onTrackMoved(from, to);
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_recycler_view_holder, parent, false);
        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.bind(track);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void setCallback(OnTrackMovedCallback callback) {
        this.callback = callback;
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {

        private TextView trackName, artists;

        TrackViewHolder(View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_name);
            artists = itemView.findViewById(R.id.artists);
        }

        void bind(Track track) {
            trackName.setText(track.getName());
            artists.setText(track.getArtistsString());
        }
    }
}
