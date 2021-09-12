package com.example.pc.basemvp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.example.pc.basemvp.R;
import com.example.pc.basemvp.base.recyclerView.EndlessRecyclerOnScrollListener;

public class SuperRecyclerView extends FrameLayout {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadDataListener mListener;

    public SuperRecyclerView(@NonNull Context context) {
        super(context);
        initView();
    }

    public SuperRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SuperRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.super_recyclerview, this, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mProgressBar = view.findViewById(R.id.progressBar);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        addView(view);
    }

    public void setLoadDataListener(LoadDataListener listener) {
        mListener = listener;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        mOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                startLoadMore();
                mListener.onLoadMore(currentPage);
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            setRefreshing(true);
            mListener.onRefreshData();
        });
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setRefreshing(boolean isRefresh) {
        mSwipeRefreshLayout.setRefreshing(isRefresh);
    }

    public void stopLoadData() {
        setRefreshing(false);
        stopLoadMore();
    }

    public void startLoadMore() {
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoadMore() {
        mRecyclerView.setOverScrollMode(OVER_SCROLL_ALWAYS);
        mProgressBar.setVisibility(View.GONE);
    }

    public void disableAnimateRecyclerView() {
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void enableAnimateRecyclerView() {
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    public void resetState() {
        ((EndlessRecyclerOnScrollListener) mOnScrollListener).reset();
    }

    public void release() {
        mSwipeRefreshLayout.setOnRefreshListener(null);
        mSwipeRefreshLayout = null;
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mRecyclerView = null;
        mProgressBar = null;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public interface LoadDataListener {
        void onLoadMore(int page);

        void onRefreshData();
    }
}
