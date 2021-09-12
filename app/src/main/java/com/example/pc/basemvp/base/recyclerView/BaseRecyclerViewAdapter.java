package com.example.pc.basemvp.base.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Base Adapter.
 *
 * @param <V> is a type extend from {@link RecyclerView.ViewHolder}
 * @param <T> is a Object
 */

public abstract class BaseRecyclerViewAdapter<T, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private final Context mContext;
    protected List<T> mDataList;
    protected OnItemClickListener<T> mItemClickListener;

    protected BaseRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    protected Context getContext() {
        return mContext;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void updateData(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDataList.clear();
    }

    protected T getItem(int position) {
        if (position < 0 || position > getItemCount()) {
            return null;
        }
        return mDataList.get(position);
    }

    public void removeItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(T data, int position) {
        mDataList.add(position, data);
        notifyItemInserted(position);
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * OnItemClickListener
     *
     * @param <T> Data from item click
     */
    public interface OnItemClickListener<T> {
        void onItemRecyclerViewClick(T item);
    }
}
