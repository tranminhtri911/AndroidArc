package com.example.mvvmkoin.screen.main.userPaging

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.mvvmkoin.R
import com.example.mvvmkoin.base.BaseFragment
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.databinding.FragmentUserPagingBinding
import com.example.mvvmkoin.screen.main.MainActivity
import com.example.mvvmkoin.util.liveData.autoCleared
import com.example.mvvmkoin.base.paging.Status.FAILED
import com.example.mvvmkoin.widget.recyclerView.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_user_favorite.recyclerView
import kotlinx.android.synthetic.main.fragment_user_paging.*
import kotlin.reflect.KClass

class UserPagingFragment : BaseFragment<FragmentUserPagingBinding, UserPagingViewModel>() {

    private var adapter by autoCleared<UserPagingAdapter>()

    override val layoutID: Int
        get() = R.layout.fragment_user_paging

    override val viewModelClass: KClass<UserPagingViewModel>
        get() = UserPagingViewModel::class

    override fun bindViewInput() {

        adapter = UserPagingAdapter(object : OnItemClickListener<User> {
            override fun onItemViewClick(item: User, position: Int, view: View?) {
                val activity = activity as MainActivity
                activity.navHelper.replaceFrag(PagingFragment.newInstance(item))
            }
        }).apply {
            recyclerView.adapter = this
            viewModel.retry()
        }

    }

    override fun bindViewOutput() {
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.repoList.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.getState().observe(this, Observer {

            swipe_refresh_layout.isRefreshing = false

            adapter.setNetworkState(it)

            if (it.status == FAILED) {
                Toast.makeText(context, it.status.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        fun newInstance() = UserPagingFragment()
        const val TAG = "UserPagingFragment"
    }

}
