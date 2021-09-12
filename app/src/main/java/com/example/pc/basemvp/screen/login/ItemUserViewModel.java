package com.example.pc.basemvp.screen.login;

import android.databinding.ObservableField;
import android.view.View;
import com.example.pc.basemvp.base.recyclerView.BaseRecyclerViewAdapter;
import com.example.pc.basemvp.data.model.User;

public class ItemUserViewModel {

    public final ObservableField<User> user = new ObservableField<>();
    private BaseRecyclerViewAdapter.OnItemClickListener<User> mItemClickListener;

    ItemUserViewModel(BaseRecyclerViewAdapter.OnItemClickListener<User> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setData(User user) {
        this.user.set(user);
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || this.user.get() == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(user.get());
    }
}
