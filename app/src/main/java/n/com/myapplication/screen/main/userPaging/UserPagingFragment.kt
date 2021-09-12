package n.com.myapplication.screen.main.userPaging

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_user_favorite.*
import n.com.myapplication.R
import n.com.myapplication.base.BaseFragment
import n.com.myapplication.data.model.User
import n.com.myapplication.databinding.FragmentUserPagingBinding
import n.com.myapplication.screen.main.MainActivity
import n.com.myapplication.util.LogUtils
import n.com.myapplication.util.extension.addFragmentToActivity
import n.com.myapplication.util.liveData.autoCleared
import n.com.myapplication.widget.recyclerView.OnItemClickListener

class UserPagingFragment : BaseFragment<FragmentUserPagingBinding, UserPagingViewModel>() {

    private var adapter by autoCleared<UserPagingAdapter>()

    override val layoutID: Int
        get() = R.layout.fragment_user_paging

    override val viewModelClass: Class<UserPagingViewModel>
        get() = UserPagingViewModel::class.java

    override fun bindViewInput() {
        adapter = UserPagingAdapter(object : OnItemClickListener<User> {
            override fun onItemViewClick(item: User, position: Int, view: View?) {
                val activity = activity as MainActivity
                activity.navHelper.addFrag(PagingFragment.newInstance(item))
            }
        }).apply { recyclerView.adapter = this }

    }

    override fun bindViewOutput() {
        viewModel.retry()

        viewModel.repoList.observe(this, Observer { resource ->
            adapter.submitList(resource)
        })

        viewModel.getState().observe(this, Observer {
            LogUtils.d(TAG, it.toString())
        })
    }

    companion object {
        fun newInstance() = UserPagingFragment()
        const val TAG = "UserPagingFragment"
    }

}
