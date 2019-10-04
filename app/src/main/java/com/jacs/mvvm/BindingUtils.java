package com.jacs.mvvm;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.jacs.mvvm.interfaces.BindableAdapter;

public class BindingUtils {
    @BindingAdapter("data")
    public static <T> void setRecyclerViewProperties(RecyclerView recyclerView, T data) {
        if (recyclerView.getAdapter() instanceof BindableAdapter) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            BindableAdapter<T> bindableAdapter = (BindableAdapter<T>) adapter;
            bindableAdapter.setData(data);
        }
    }
}
