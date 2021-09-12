package n.com.myapplication.screen.main.user

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_user.*
import n.com.myapplication.R
import n.com.myapplication.base.BaseFragment
import n.com.myapplication.data.model.User
import n.com.myapplication.databinding.FragmentUserBinding
import n.com.myapplication.screen.main.MainActivityViewModel
import n.com.myapplication.screen.userDetail.UserDetailActivity
import n.com.myapplication.util.RxView
import n.com.myapplication.util.liveData.NetWorkState
import n.com.myapplication.util.liveData.autoCleared
import n.com.myapplication.widget.recyclerView.OnItemClickListener
import n.com.myapplication.widget.superRecyclerView.SuperRecyclerView

class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>(), OnItemClickListener<User>,
        SuperRecyclerView.LoadDataListener {

    lateinit var mainViewModel: MainActivityViewModel

    private var adapter by autoCleared<UserAdapter>()

    override val layoutID: Int
        get() = R.layout.fragment_user

    override val viewModelClass: Class<UserViewModel>
        get() = UserViewModel::class.java

    override fun bindViewInput() {
        mainViewModel = ViewModelProvider(activity!!, viewModelFactory).get(MainActivityViewModel::class.java)

        menu.setOnClickListener {
            mainViewModel.actionShowDrawer.postValue(null)
        }

        adapter = UserAdapter().apply { registerItemClickListener(this@UserFragment) }

        recyclerView.apply {
            setUpView(adapter = adapter)
            setLoadDataListener(this@UserFragment)
        }

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchUser(NetWorkState.REFRESH)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewModel.searchUser(netWorkState = NetWorkState.FETCH)

        viewModel.initRxSearch(searchObservable = RxView.search(edtSearch))
    }

    override fun bindViewOutput() {
        viewModel.repoList.observe(this, Observer { resource ->
            val data = resource.data
            when (resource.netWorkState) {
                NetWorkState.FETCH -> {
                    dialogManager.hideLoading()
                    adapter.updateData(data)
                }
                NetWorkState.REFRESH -> {
                    recyclerView.stopRefreshData()
                    recyclerView.refreshAdapter()
                    adapter.updateData(data)
                }
                NetWorkState.LOAD_MORE -> {
                    recyclerView.stopLoadMore()
                    adapter.addData(data)
                }
                NetWorkState.ERROR -> {
                    recyclerView.stopRefreshData()
                    recyclerView.stopLoadMore()
                    onHandleError(resource.error)
                }
                else -> {
                }
            }
        })
    }


    override fun onItemViewClick(item: User, position: Int, view: View?) {
        item.isFavorite = !item.isFavorite
        viewModel.pressFavorite(item)

        val intent = UserDetailActivity.getInstance(context, item) ?: return

        val isTransitionTo = position % 2 == 0

        if (isTransitionTo) {
            transitionTo(intent)
        } else {
            val imgAvatar = view?.findViewById<ImageView>(R.id.imgAvatar) ?: return
            val shareAvatar = Pair<View, String>(imgAvatar,
                    getString(R.string.transition_avatar_user))
            val transitionOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,
                    shareAvatar)
            startActivity(intent, transitionOptions.toBundle())
        }
    }

    override fun onLoadMore(page: Int) {
        viewModel.searchUser(NetWorkState.LOAD_MORE, page = page)
    }

    override fun onRefreshData() {
        viewModel.searchUser(NetWorkState.REFRESH)
    }

    companion object {
        private const val TAG = "UserFragment"
        fun newInstance() = UserFragment()
    }
}
