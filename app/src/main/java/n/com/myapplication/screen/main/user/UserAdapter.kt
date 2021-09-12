package n.com.myapplication.screen.main.user

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import n.com.myapplication.R
import n.com.myapplication.data.model.User
import n.com.myapplication.util.extension.inflateBinding
import n.com.myapplication.widget.recyclerView.BaseItemVH
import n.com.myapplication.widget.recyclerView.LoadMoreAdapter
import n.com.myapplication.widget.recyclerView.OnItemClickListener

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
            : BaseItemVH<User>(binding, ItemUserViewModel(), listener)

    }
}