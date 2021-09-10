package com.example.mvvmkoin.screen.main.userFavorite

import android.view.View
import androidx.lifecycle.Observer
import com.example.mvvmkoin.R
import com.example.mvvmkoin.base.BaseFragment
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.databinding.FragmentUserFavoriteBinding
import com.example.mvvmkoin.screen.main.user.UserAdapter
import com.example.mvvmkoin.util.LogUtils
import com.example.mvvmkoin.util.liveData.autoCleared
import com.example.mvvmkoin.widget.recyclerView.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_user_favorite.*
import kotlin.reflect.KClass


class UserFavoriteFragment : BaseFragment<FragmentUserFavoriteBinding, UserFavoriteViewModel>(),
    OnItemClickListener<User> {

    private var adapter by autoCleared<UserAdapter>()

    override val viewModelClass: KClass<UserFavoriteViewModel>
        get() = UserFavoriteViewModel::class

    override val layoutID: Int
        get() = R.layout.fragment_user_favorite

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

