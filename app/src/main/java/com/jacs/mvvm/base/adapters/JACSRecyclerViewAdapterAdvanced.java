package com.jacs.mvvm.base.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jacs.mvvm.interfaces.JACSBindableAdapter;
import com.jacs.mvvm.interfaces.JACSOnRecyclerViewItemClicked;

import java.util.ArrayList;
import java.util.List;

public abstract class JACSRecyclerViewAdapterAdvanced<T, L, VH extends JACSViewHolderAdvanced<T, L>> extends RecyclerView.Adapter<VH> implements JACSBindableAdapter<T> {

    protected List<T> items;
    private JACSOnRecyclerViewItemClicked<L> listener;
    private LayoutInflater layoutInflater;

    @Override
    public abstract void setData(List<T> data);

    /**
     * Base constructor.
     * Allocate adapter-related objects here if needed.
     *
     * @param context Context needed to retrieve LayoutInflater
     */
    public JACSRecyclerViewAdapterAdvanced(Context context) {
        layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    /**
     * To be implemented in as specific adapter
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    @NonNull
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the itemView to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = items.get(position);
        holder.position = position;
        holder.onBind(item, listener);
    }

    /**
     * Returns the total number of mData in the data set held by the adapter.
     *
     * @return The total number of mData in this adapter.
     */
    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    /**
     * Sets mData to the adapter and notifies that data set has been changed.
     *
     * @param items mData to set to the adapter
     * @throws IllegalArgumentException in case of setting `null` mData
     */
    public void setItems(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot set `null` item to the Recycler adapter");
        }
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Returns all mData from the data set held by the adapter.
     *
     * @return All of mData in this adapter.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Returns an mData from the data set at a certain position.
     *
     * @return All of mData in this adapter.
     */
    public T getItem(int position) {
        return items.get(position);
    }

    /**
     * Adds item to the end of the data set.
     * Notifies that item has been inserted.
     *
     * @param item item which has to be added to the adapter.
     */
    public void addItem(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to the Recycler adapter");
        }
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    /**
     * Adds list of mData to the end of the adapter's data set.
     * Notifies that item has been inserted.
     *
     * @param items mData which has to be added to the adapter.
     */
    public void addAll(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot add `null` mData to the Recycler adapter");
        }
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size() - items.size(), items.size());
    }

    /**
     * Clears all the mData in the adapter.
     */
    public void clearData() {
        items.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes an item from the adapter.
     * Notifies that item has been removed.
     *
     * @param item to be removed
     */
    public void removeItem(T item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Returns whether adapter is empty or not.
     *
     * @return `true` if adapter is empty or `false` otherwise
     */
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    /**
     * Set click listener, which must extend {@link JACSOnRecyclerViewItemClicked}
     *
     * @param listener click listener
     */
    public void setItemClickedListener(JACSOnRecyclerViewItemClicked<L> listener) {
        this.listener = listener;
    }

    /**
     * Inflates a view.
     *
     * @param layout       layout to me inflater
     * @param parent       container where to inflate
     * @param attachToRoot whether to attach to root or not
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
        return layoutInflater.inflate(layout, parent, attachToRoot);
    }

    /**
     * Inflates a view.
     *
     * @param layout layout to me inflater
     * @param parent container where to inflate
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout, parent, false);
    }

}
