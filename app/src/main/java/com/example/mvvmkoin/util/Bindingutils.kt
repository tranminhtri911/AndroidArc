@file:Suppress("DEPRECATION")

package com.example.mvvmkoin.util

import android.graphics.Paint
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout

object BindingUtils {

    @JvmStatic
    @BindingAdapter("currentPage")
    fun setCurrentViewPager(viewPager: ViewPager, currentPage: Int) {
        viewPager.currentItem = currentPage
    }

    @JvmStatic
    @BindingAdapter("viewPagerAdapter")
    fun setViewPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter) {
        viewPager.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("viewPager")
    fun setViewPagerTabs(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.setupWithViewPager(viewPager, true)
    }

    @JvmStatic
    @BindingAdapter("hasFixedSize")
    fun setHasFixedSize(view: RecyclerView, isFixed: Boolean) {
        view.setHasFixedSize(isFixed)
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context).load(url).transition(
                DrawableTransitionOptions.withCrossFade()).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imageCircleUrl")
    fun setImageCircleUrl(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("invisible")
    fun setInvisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("isRefreshing")
    fun setSwipeRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
        view.isRefreshing = isRefreshing
    }


    @JvmStatic
    @BindingAdapter("parseHtmlToText")
    fun parseHtmlToText(textView: TextView, bodyData: String) {
        if (TextUtils.isEmpty(bodyData)) {
            return
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY)
        } else {
            textView.text = Html.fromHtml(bodyData)
        }
    }

    @JvmStatic
    @BindingAdapter("setTextUnderLine")
    fun setTextUnderLine(textView: TextView, content: String) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        textView.text = content
    }


}