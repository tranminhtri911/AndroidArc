package com.example.mvvmkoin.screen.main.user

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkoin.R
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.util.extension.inflateBinding
import com.example.mvvmkoin.widget.recyclerView.BaseItemVH
import com.example.mvvmkoin.widget.recyclerView.LoadMoreAdapter
import com.example.mvvmkoin.widget.recyclerView.OnItemClickListener

class UserAdapter : LoadMoreAdapter<User>() {

    override fun getViewType(position: Int): Int {
        return 0
    }

    @NonNull
    override fun onCreateVH(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(listener = itemClickListener,
                binding = parent.inflateBinding(R.layout.item_user))
    }

    override fun onBindVH(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(data = getItem(position))
    }

    companion object {

        class ItemViewHolder(binding: ViewDataBinding,
                listener: OnItemClickListener<User>?)
            : BaseItemVH<User, ItemUserViewModel>(binding, listener) {

            override val createVM: ItemUserViewModel
                get() = ItemUserViewModel()
        }

    }
}