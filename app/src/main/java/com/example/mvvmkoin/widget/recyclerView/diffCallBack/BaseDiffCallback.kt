package com.example.mvvmkoin.widget.recyclerView.diffCallBack

import androidx.recyclerview.widget.DiffUtil
import com.example.mvvmkoin.data.model.BaseModel
import javax.annotation.Nullable

class BaseDiffCallback<T>
(@Nullable private var olds: MutableList<T>, @Nullable private var news: MutableList<T>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return olds.size
    }

    override fun getNewListSize(): Int {
        return news.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: BaseModel<*>? = olds[oldItemPosition] as BaseModel<*>?
        val newItem: BaseModel<*>? = news[newItemPosition] as BaseModel<*>?
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: BaseModel<*>? = olds[oldItemPosition]  as BaseModel<*>?
        val newItem: BaseModel<*>? = news[newItemPosition] as BaseModel<*>?
        return oldItem == newItem
    }
}