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

public abstract class JACSRecyclerViewAdapter<T, VH extends JACSViewHolder<T>> extends RecyclerView.Adapter<VH> implements JACSBindableAdapter<T> {

    protected List<T> mData;
    private JACSOnRecyclerViewItemClicked<T> listener;
    private LayoutInflater layoutInflater;

    @Override
    public abstract void setData(List<T> data);

    /**
     * Base constructor.
     * Allocate adapter-related objects here if needed.
     *
     * @param context Context needed to retrieve LayoutInflater
     */
    public JACSRecyclerViewAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
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
        T item = mData.get(position);
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
        return mData != null ? mData.size() : 0;
    }

    /**
     * Sets mData to the adapter and notifies that data set has been changed.
     *
     * @param mData mData to set to the adapter
     * @throws IllegalArgumentException in case of setting `null` mData
     */
    public void setmData(List<T> mData) {
        if (mData == null) {
            throw new IllegalArgumentException("Cannot set `null` item to the Recycler adapter");
        }
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    /**
     * Returns all mData from the data set held by the adapter.
     *
     * @return All of mData in this adapter.
     */
    public List<T> getmData() {
        return mData;
    }

    /**
     * Returns an mData from the data set at a certain position.
     *
     * @return All of mData in this adapter.
     */
    public T getItem(int position) {
        return mData.get(position);
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
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
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
        this.mData.addAll(items);
        notifyItemRangeInserted(this.mData.size() - items.size(), items.size());
    }

    /**
     * Clears all the mData in the adapter.
     */
    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes an item from the adapter.
     * Notifies that item has been removed.
     *
     * @param item to be removed
     */
    public void removeItem(T item) {
        int position = mData.indexOf(item);
        if (position > -1) {
            mData.remove(position);
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
     * Indicates whether each item in the data set can be represented with a unique identifier
     * of type {@link Long}.
     *
     * @param hasStableIds Whether mData in data set have unique identifiers or not.
     * @see #hasStableIds()
     * @see #getItemId(int)
     */
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }


    /**
     * Set click listener, which must extend {@link JACSOnRecyclerViewItemClicked}
     *
     * @param listener click listener
     */
    public void setItemClickedListener(JACSOnRecyclerViewItemClicked<T> listener) {
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
