package com.example.mvvmkoin.widget.superRecyclerView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mvvmkoin.R
import com.example.mvvmkoin.util.Constant
import com.example.mvvmkoin.util.LogUtils
import com.example.mvvmkoin.util.extension.notNull
import com.example.mvvmkoin.widget.recyclerView.EndlessRecyclerOnScrollListener
import com.example.mvvmkoin.widget.recyclerView.LoadMoreAdapter

class SuperRecyclerView : FrameLayout {

    private lateinit var recyclerView: RecyclerView
    private lateinit var onScrollListener: EndlessRecyclerOnScrollListener
    private lateinit var loadMoreAdapter: LoadMoreAdapter<*>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var isRefresh = false
    private var currentPage = Constant.PAGE_DEFAULT

    private var listener: LoadDataListener? = null

    constructor(@NonNull context: Context) : super(context) {
        initView()
    }

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet,
            defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.super_recyclerview, this, false)

        recyclerView = view.findViewById(R.id.super_recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setColorSchemeColors(*colorForSmart)

        addView(view)
    }

    fun setUpView(adapter: LoadMoreAdapter<*>,
            layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)) {
        loadMoreAdapter = adapter
        recyclerView.adapter = adapter

        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = getSpanSizeLookup(layoutManager)
        }
        recyclerView.layoutManager = layoutManager

        onScrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                startLoadMore()
            }
        }
        recyclerView.addOnScrollListener(onScrollListener)

        swipeRefreshLayout.setOnRefreshListener { startRefreshData() }
    }

    fun startLoadMore() {
        if (loadMoreAdapter.isLoadMore || isRefresh) {
            return
        }
        currentPage++
        recyclerView.post { loadMoreAdapter.onStartLoadMore() }
        listener?.onLoadMore(currentPage)
    }

    fun stopLoadMore() {
        loadMoreAdapter.onStopLoadMore()
    }

    private fun startRefreshData() {
        if (isRefresh) return

        isRefresh = true
        currentPage = Constant.PAGE_DEFAULT

        swipeRefreshLayout.isRefreshing = true
        listener?.onRefreshData()
    }

    fun stopRefreshData() {
        if (!isRefresh) return
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
    }

    fun refreshAdapter() {
        stopLoadMore()
        resetScrollState()
    }

    fun disableAnimate() {
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    fun enableAnimate() {
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = true
    }

    fun setHasFixedSize(isFixed: Boolean) {
        recyclerView.setHasFixedSize(isFixed)
    }

    fun setEnableSwipe(isEnable: Boolean) {
        swipeRefreshLayout.isEnabled = isEnable
    }

    private fun getSpanSizeLookup(
            layoutManager: GridLayoutManager): GridLayoutManager.SpanSizeLookup {
        val spanSize = layoutManager.spanCount
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (loadMoreAdapter.isLoadMore
                        && position == loadMoreAdapter.bottomItemPosition()) {
                    spanSize
                } else 1
            }
        }
    }

    private fun resetScrollState() {
        onScrollListener.reset()
    }

    fun setLoadDataListener(listener: LoadDataListener) {
        this.listener = listener
    }

    interface LoadDataListener {
        fun onLoadMore(page: Int)

        fun onRefreshData()
    }

    override fun onDetachedFromWindow() {
        listener = null
        loadMoreAdapter.notNull { it.unRegisterItemClickListener() }
        swipeRefreshLayout.notNull { it.setOnRefreshListener(null) }
        onScrollListener.notNull { recyclerView.removeOnScrollListener(it) }
        LogUtils.d(TAG, Constant.RELEASED)
        super.onDetachedFromWindow()
    }

    companion object {
        private val TAG = SuperRecyclerView::class.java.simpleName
        val colorForSmart = intArrayOf(Color.rgb(30, 186, 177), Color.rgb(0xF7, 0xD2, 0x3E),
                Color.rgb(0xF7, 0xD2, 0x3E), Color.rgb(0x34, 0xA3, 0x50))
        val colorForRing = intArrayOf(Color.rgb(0xF7, 0xD2, 0x3E), Color.rgb(0xF7, 0xD2, 0x3E),
                Color.rgb(30, 186, 177), Color.rgb(0x34, 0xA3, 0x50))
    }
}
