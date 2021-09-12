package n.com.myapplication.widget.recyclerView

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import n.com.myapplication.R
import n.com.myapplication.util.LogUtils
import n.com.myapplication.util.extension.inflateBinding

@Suppress("UNCHECKED_CAST")
abstract class LoadMoreAdapter<T> : BaseRecyclerViewAdapter<T, RecyclerView.ViewHolder>() {

    var isLoadMore = false

    @NonNull
    override fun getItemViewType(position: Int): Int {
        if (isLoadMore && position == bottomItemPosition()) {
            return TYPE_PROGRESS
        }
        return getViewType(position)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (TYPE_PROGRESS == viewType) {
            return ItemLoadMoreVH(parent.inflateBinding(R.layout.item_load_more))
        }
        return onCreateVH(parent, viewType)
    }

    @NonNull
    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (TYPE_PROGRESS == holder.itemViewType) {
            (holder as ItemLoadMoreVH).bind(isLoadMore)
            return
        }
        onBindVH(holder, position)
    }

    fun bottomItemPosition(): Int = itemCount - 1

    fun onStartLoadMore() {
        if (dataList.isEmpty() || isLoadMore) return
        isLoadMore = true
        addItem(null as T, itemCount)
        LogUtils.d(TAG, START_LOAD_MORE)
    }

    fun onStopLoadMore() {
        if (!isLoadMore) return
        isLoadMore = false
        removeItem(bottomItemPosition())
        LogUtils.d(TAG, STOP_LOAD_MORE)
    }

    protected abstract fun onCreateVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    protected abstract fun onBindVH(holder: RecyclerView.ViewHolder, position: Int)

    protected abstract fun getViewType(position: Int): Int

    companion object {
        private const val TYPE_PROGRESS = 0xFFFF
        private const val TAG = "LoadMoreAdapter"
        private const val START_LOAD_MORE = "START_LOAD_MORE"
        private const val STOP_LOAD_MORE = "STOP_LOAD_MORE"

        class ItemLoadMoreVH(binding: ViewDataBinding) : BaseItemVH<Boolean>(binding,
                ItemLoadMoreViewModel())
    }
}