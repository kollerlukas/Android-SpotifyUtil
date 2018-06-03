package us.koller.spotifyutil.dragRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Helper for enabling Drag & Drop in the RecyclerView
 **/
public class DragHelperCallback extends ItemTouchHelper.Callback {

    private DragAdapter adapter;
    private boolean itemDragged;
    private int from, to;

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // draging up and down is allowed
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // swiping is not allowed in any direction
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        if (adapter == null) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter instanceof DragAdapter) {
                this.adapter = (DragAdapter) adapter;
            }
        }
        // save moved positions
        if (!itemDragged) {
            from = source.getAdapterPosition();
            Log.d("DragHelperCallback", "onMove: from: " + from);
        }
        to = target.getAdapterPosition();
        itemDragged = true;
        // notify the adapter
        adapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // nothing
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && itemDragged) {
            itemDragged = false;
            // item was dragged & dropped
            if (adapter != null) {
                // notify the adapter
                adapter.onItemMoved(from, to);
                from = -1;
                to = -1;
            }
        }
    }
}
