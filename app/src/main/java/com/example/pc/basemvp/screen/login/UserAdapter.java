package com.example.pc.basemvp.screen.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.pc.basemvp.R;
import com.example.pc.basemvp.base.recyclerView.BaseRecyclerViewAdapter;
import com.example.pc.basemvp.data.model.User;
import com.example.pc.basemvp.databinding.ItemUserBinding;

public class UserAdapter extends BaseRecyclerViewAdapter<User, UserAdapter.ItemUserViewHolder> {

    protected UserAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ItemUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_user, parent, false);
        return new UserAdapter.ItemUserViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemUserViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ItemUserViewHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding mBinding;
        private ItemUserViewModel mViewModel;

        ItemUserViewHolder(ItemUserBinding binding, OnItemClickListener<User> itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new ItemUserViewModel(itemClickListener);
            mBinding.setViewModel(mViewModel);
        }

        void bind(User user) {
            mViewModel.setData(user);
            mBinding.executePendingBindings();
        }
    }
}
