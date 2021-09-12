package n.com.myapplication.screen.main.userPaging

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import n.com.myapplication.R
import n.com.myapplication.data.model.User
import n.com.myapplication.screen.main.user.ItemUserViewModel
import n.com.myapplication.util.extension.inflateBinding
import n.com.myapplication.widget.recyclerView.BaseItemVH
import n.com.myapplication.widget.recyclerView.OnItemClickListener

class UserPagingAdapter
constructor(private val itemClickListener: OnItemClickListener<User>?)
    : PagedListAdapter<User, UserPagingAdapter.Companion.ItemViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup,
            viewType: Int): ItemViewHolder {
        return ItemViewHolder(binding = parent.inflateBinding(R.layout.item_user),
                listener = itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DIFF_CALLBACK = object :
                DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldConcert: User,
                    newConcert: User) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: User,
                    newConcert: User) = oldConcert == newConcert
        }

        class ItemViewHolder(binding: ViewDataBinding,
                listener: OnItemClickListener<User>?) : BaseItemVH<User>(binding,
                ItemUserViewModel(), listener)

    }
}

