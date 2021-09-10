package com.example.mvvmkoin.base.paging

import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePageListAdapter<T>
    (diffUtil: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffUtil) {

    private var networkState: NetworkState? = null


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == networkStatePosition()) {
            TYPE_NETWORK_STATE
        } else {
            getViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NETWORK_STATE -> NetworkStateViewHolder.create(parent)
            else -> onCreateVH(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NetworkStateViewHolder) {
            holder.bindTo(networkState = networkState)
            return
        }
        onBindVH(holder, position)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }


    private fun networkStatePosition(): Int = itemCount - 1


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()

        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else {
            if (hasExtraRow && previousState !== newNetworkState) {
                notifyItemChanged(networkStatePosition())
            }
        }
    }


    protected abstract fun onCreateVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    protected abstract fun onBindVH(holder: RecyclerView.ViewHolder, position: Int)

    protected abstract fun getViewType(position: Int): Int


    companion object {
        private const val TYPE_NETWORK_STATE = 0xFFFF
        private const val TAG = "BasePageListAdapter"

        val config = PagedList.Config.Builder()
            .setPageSize(25)
            .setInitialLoadSizeHint(25)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(false)
            .build()
    }

}