package com.jacs.mvvm;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jacs.mvvm.interfaces.JACSBindableAdapter;

import java.util.List;

public class JACSBindingUtils {
    @BindingAdapter("data")
    public static <T extends List> void setRecyclerViewProperties(RecyclerView recyclerView, T data) {
        if (recyclerView.getAdapter() instanceof JACSBindableAdapter) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            JACSBindableAdapter<T> JACSBindableAdapter = (JACSBindableAdapter<T>) adapter;
            JACSBindableAdapter.setData(data);
        }
    }
}
