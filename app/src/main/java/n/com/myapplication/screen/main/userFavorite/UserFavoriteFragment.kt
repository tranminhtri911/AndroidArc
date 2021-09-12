package n.com.myapplication.screen.main.userFavorite

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_user_favorite.*
import n.com.myapplication.R
import n.com.myapplication.base.BaseFragment
import n.com.myapplication.data.model.User
import n.com.myapplication.databinding.FragmentUserFavoriteBinding
import n.com.myapplication.screen.main.user.UserAdapter
import n.com.myapplication.util.LogUtils
import n.com.myapplication.util.liveData.autoCleared
import n.com.myapplication.widget.recyclerView.OnItemClickListener


class UserFavoriteFragment : BaseFragment<FragmentUserFavoriteBinding, UserFavoriteViewModel>(), OnItemClickListener<User> {


    private var adapter by autoCleared<UserAdapter>()

    override val layoutID: Int
        get() = R.layout.fragment_user_favorite

    override val viewModelClass: Class<UserFavoriteViewModel>
        get() = UserFavoriteViewModel::class.java

    override fun bindViewInput() {
        adapter = UserAdapter().apply {
            recyclerView.adapter = this
            registerItemClickListener(this@UserFavoriteFragment)
        }
    }

    override fun bindViewOutput() {
        viewModel.repoList.observe(this, Observer { data ->
            LogUtils.d(TAG, data.size.toString())
            adapter.updateData(data)
        })
    }

    override fun onItemViewClick(item: User, position: Int, view: View?) {
        viewModel.unFavorite(item)
    }

    companion object {
        fun newInstance() = UserFavoriteFragment()
        const val TAG = "UserFavoriteFragment"
    }

}

