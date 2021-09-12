package n.com.myapplication.widget.recyclerView

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import n.com.myapplication.BR

abstract class BaseItemVH<T>
constructor
(private val binding: ViewDataBinding,
        private val viewModel: BaseItemVM<T>,
        private val listener: OnItemClickListener<T>? = null)
    : RecyclerView.ViewHolder(binding.root) {

    init {
        with(viewModel) {
            binding = this@BaseItemVH.binding
            listener = this@BaseItemVH.listener
        }
        binding.setVariable(BR.viewModel, viewModel)
    }

    fun bind(data: T?) {
        viewModel.position = adapterPosition
        viewModel.bindData(data)
        binding.executePendingBindings()
    }

}