package us.koller.spotifyutil;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import us.koller.spotifyutil.model.Playlist;

/**
 * Playlist RecyclerView Adapter class
 */
@SuppressWarnings("WeakerAccess")
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface PlaylistClickListener {
        public void onPlaylistClicked(String playlist_id);
    }

    private List<Playlist> playlists;

    private PlaylistClickListener clickListener;

    public PlaylistAdapter() {
        playlists = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_recycler_view_item, parent, false);
        return new PlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.bind(playlist);

        holder.itemView.setOnClickListener(view -> {
            // Playlist clicked
            String playlistId = (String) view.getTag();
            clickListener.onPlaylistClicked(playlistId);
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setClickListener(PlaylistClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Playlist View Holder class
     */
    static class PlaylistViewHolder extends RecyclerView.ViewHolder {

        private ImageView playlistImage;
        private TextView playlistName, playlistOwner;

        PlaylistViewHolder(View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistName = itemView.findViewById(R.id.playlist_name);
            playlistOwner = itemView.findViewById(R.id.playlist_owner);
        }

        void bind(Playlist playlist) {
            // set the playlist_id as the tag for the ViewHolder
            itemView.setTag(playlist.getId());
            // load the playlist image
            Glide.with(playlistImage.getContext())
                    .load(playlist.getImages()[0].getUrl())
                    .into(playlistImage);
            // set name & owner
            playlistName.setText(playlist.getName());
            playlistOwner.setText(playlist.getOwner().getDisplay_name());
        }
    }
}
