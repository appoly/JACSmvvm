package com.jacs.mvvm.base.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jacs.mvvm.interfaces.JACSOnRecyclerViewItemClicked;

public abstract class JACSViewHolderAdvanced<T, L> extends RecyclerView.ViewHolder {

    public int position;

    public JACSViewHolderAdvanced(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Bind data to the item and set listener if needed.
     * @param item     object, associated with the item.
     * @param listener listener a listener {@link JACSOnRecyclerViewItemClicked} which has to be set at on the item (if not `null`).
     */
    public abstract void onBind(T item, @Nullable JACSOnRecyclerViewItemClicked<L> listener);

}
