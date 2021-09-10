package com.example.mvvmkoin.screen.main.userPaging

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkoin.R
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.screen.main.user.ItemUserViewModel
import com.example.mvvmkoin.util.extension.inflateBinding
import com.example.mvvmkoin.base.paging.BasePageListAdapter
import com.example.mvvmkoin.widget.recyclerView.BaseItemVH
import com.example.mvvmkoin.widget.recyclerView.OnItemClickListener

class UserPagingAdapter
constructor(private val itemClickListener: OnItemClickListener<User>?) :
    BasePageListAdapter<User>(DIFF_CALLBACK) {

    override fun onCreateVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            binding = parent.inflateBinding(R.layout.item_user),
            listener = itemClickListener
        )
    }

    override fun onBindVH(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getViewType(position: Int): Int = 0


    companion object {

        val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldConcert: User, newConcert: User) =
                oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: User, newConcert: User) =
                oldConcert == newConcert
        }

        class ItemViewHolder(
            binding: ViewDataBinding, listener: OnItemClickListener<User>?
        ) : BaseItemVH<User, ItemUserViewModel>(binding, listener) {

            override val createVM: ItemUserViewModel
                get() = ItemUserViewModel()

        }

    }
}

