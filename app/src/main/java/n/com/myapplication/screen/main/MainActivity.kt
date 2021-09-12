package n.com.myapplication.screen.main

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import n.com.myapplication.R
import n.com.myapplication.base.BaseActivity
import n.com.myapplication.databinding.ActivityMainBinding
import n.com.myapplication.screen.main.user.UserFragment
import n.com.myapplication.screen.main.userFavorite.UserFavoriteFragment
import n.com.myapplication.screen.main.userPaging.UserPagingFragment
import n.com.myapplication.util.LogUtils
import n.com.myapplication.util.extension.show
import n.com.myapplication.util.navigation.NavHelper
import n.com.myapplication.util.navigation.NavHelperImpl

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private val userFragment: UserFragment = UserFragment.newInstance()
    private val mUserFavoriteFragment: UserFavoriteFragment by lazy { UserFavoriteFragment.newInstance() }
    private val userPagingFragment: UserPagingFragment by lazy { UserPagingFragment.newInstance() }

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var isDoubleTapBack = false

    private val tabs: MutableList<Int> by lazy {
        mutableListOf<Int>().apply {
            add(TAB1)
            add(TAB2)
            add(TAB3)
            add(TAB4)
            add(TAB5)
        }
    }

    val navHelper: NavHelper by lazy {
        NavHelperImpl.createBuilder()
                .tabs(tabs = tabs)
                .mainFraManager(fragManager = supportFragmentManager)
                .mainContainerViewId(resLayoutID = R.id.mainContainer)
                .setup()
    }

    private val onNavigationItemSelectedListener by lazy {
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> navHelper.switchTab(tab = TAB1, fragment = userFragment)
                R.id.tab2 -> navHelper.switchTab(tab = TAB2, fragment = mUserFavoriteFragment)
                R.id.tab3 -> navHelper.switchTab(tab = TAB3, fragment = userPagingFragment)
                R.id.tab4 -> navHelper.switchTab(tab = TAB4, fragment = UserFragment.newInstance())
                R.id.tab5 -> navHelper.switchTab(tab = TAB5, fragment = UserFragment.newInstance())
            }
            return@OnNavigationItemSelectedListener true
        }
    }

    override val layoutID: Int
        get() = R.layout.activity_main

    override val viewModelClass: Class<MainActivityViewModel>
        get() = MainActivityViewModel::class.java

    override fun bindViewInput() {
        enterTransition()

        navHelper.replaceFrag(fragment = userFragment)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable { isDoubleTapBack = false }

        bottomNav.selectedItemId = R.id.tab1
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        viewModel.actionShowDrawer.observe(this, Observer {
            drawer.openDrawer(Gravity.START)

        })
    }

    override fun bindViewOutput() {
        //No-Op
    }

    override fun onBackPressed() {
        if (navHelper.isCanPopBack()) {
            return
        }
        if (navHelper.getCurrentTab() != TAB1) {
            bottomNav.selectedItemId = R.id.tab1
            navHelper.switchTab(TAB1, userFragment)
            return
        }
        if (isDoubleTapBack) {
            finish()
            return
        }
        isDoubleTapBack = true
        Toast.makeText(this, "please click again to exit", Toast.LENGTH_SHORT).show()
        handler.postDelayed(runnable, DELAY_TIME_TWO_TAP_BACK_BUTTON.toLong())
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        bottomNav.setOnNavigationItemReselectedListener(null)
        super.onDestroy()
    }

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

        const val DELAY_TIME_TWO_TAP_BACK_BUTTON = 2000
        const val TAB1 = 0
        const val TAB2 = 1
        const val TAB3 = 2
        const val TAB4 = 3
        const val TAB5 = 4
    }
}
