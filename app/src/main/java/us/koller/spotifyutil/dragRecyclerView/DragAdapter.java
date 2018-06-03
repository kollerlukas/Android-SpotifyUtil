package us.koller.spotifyutil.dragRecyclerView;

/**
 * Interface for the DragRecyclerViewAdapter
 * **/
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface DragAdapter {

    public void onItemMove(int from, int to);

    public void onItemMoved(int from, int to);
}
