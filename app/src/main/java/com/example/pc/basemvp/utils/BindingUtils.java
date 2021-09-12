package com.example.pc.basemvp.utils;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.MenuRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pc.basemvp.R;

public final class BindingUtils {
    private static final String TAG = "BindingUtils";

    public BindingUtils() {
        // No-op
    }

    @BindingAdapter("recyclerAdapter")
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({ "itemDecorator" })
    public static void setItemDecorator(RecyclerView recyclerView,
            RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    @BindingAdapter({ "currentPage" })
    public static void setCurrentViewPager(final ViewPager viewPager, final int currentPage) {
        viewPager.setCurrentItem(currentPage);
    }

    @BindingAdapter({ "viewPagerAdapter" })
    public static void setViewPagerAdapter(final ViewPager viewPager, final PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("viewPager")
    public static void setViewPagerTabs(final TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @SuppressLint("CheckResult")
    @BindingAdapter(value = { "imageUrl", "placeHolder" }, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable placeHolder) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(GenericTransitionOptions.with(R.anim.fade_in_glide))
                .into(imageView);
    }

    @BindingAdapter("setVisibility")
    public static void setVisibility(final View view, final boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("RestrictedApi")
    @BindingAdapter(value = { "menuItemClickListener", "menuId" }, requireAll = false)
    public static void showPopPupMenu(final View view,
            final PopupMenu.OnMenuItemClickListener listener, @MenuRes int menuId) {
        view.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            MenuInflater menuInflater = popupMenu.getMenuInflater();
            menuInflater.inflate(menuId, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(listener);
            MenuPopupHelper menuHelper =
                    new MenuPopupHelper(view.getContext(), (MenuBuilder) popupMenu.getMenu(), view);
            menuHelper.setForceShowIcon(true);
            menuHelper.show();
        });
    }

    @BindingAdapter({ "contentHtml" })
    public static void loadDataWebView(final WebView webView, final String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        String first = webView.getResources().getString(R.string.text_html_mark_before_content);
        String last = webView.getResources().getString(R.string.text_html_mark_after_content);
        webView.loadData(first + content + last, "text/html; charset=utf-8", "utf-8");
    }
}
