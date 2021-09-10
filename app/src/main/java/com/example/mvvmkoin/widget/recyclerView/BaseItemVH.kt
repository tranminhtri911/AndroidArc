package com.example.mvvmkoin.widget.recyclerView

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkoin.BR

/**
 * BaseItemVH
 *
 * @param <T> is Object
 * @param <VM> is ViewModel
 *
 */

abstract class BaseItemVH<T, VM : BaseItemVM<T>>
constructor(
    private val binding: ViewDataBinding,
    private val listener: OnItemClickListener<T>? = null
) : RecyclerView.ViewHolder(binding.root) {

    private val viewModel: BaseItemVM<T> by lazy {
        createVM.apply {
            binding = this@BaseItemVH.binding
            listener = this@BaseItemVH.listener
        }
    }

    init {
        binding.setVariable(BR.viewModel, viewModel)
    }

    fun bind(data: T?) {
        viewModel.position = adapterPosition
        viewModel.bindData(data)
        binding.executePendingBindings()
    }

    protected abstract val createVM: VM
}