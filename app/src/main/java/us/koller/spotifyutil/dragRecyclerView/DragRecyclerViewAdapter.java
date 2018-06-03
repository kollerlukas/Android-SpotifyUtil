package us.koller.spotifyutil.dragRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

/**
 * Wrapper for the DragAdapter & DragHelperCallback
 * **/
public abstract class DragRecyclerViewAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> implements DragAdapter {

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // attach ItemTouchHelper with the DragHelperCallback
        ItemTouchHelper touchHelper = new ItemTouchHelper(new DragHelperCallback());
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public abstract List<T> getItems();

    @Override
    public void onItemMove(int from, int to) {
        // move the item
        List<T> items = getItems();
        T item = items.get(from);
        items.remove(from);
        items.add(to, item);
        // notify the adpater
        notifyItemMoved(from, to);
    }
}
