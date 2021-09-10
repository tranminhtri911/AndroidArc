package com.example.mvvmkoin.screen.main.user

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.util.extension.notNull
import com.example.mvvmkoin.widget.recyclerView.BaseItemVM

class ItemUserViewModel : BaseItemVM<User>() {

    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @Bindable
    var user: User? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.user)
        }

    override fun bindData(data: User?) {
        user = data
        name = position.toString() + " : " + data?.fullName
    }

    override fun onItemClicked(view: View) {
        user.notNull {
            listener?.onItemViewClick(it, position, view)
        }
    }
}