package n.com.myapplication.screen.main.user

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import n.com.myapplication.data.model.User
import n.com.myapplication.util.extension.notNull
import n.com.myapplication.widget.recyclerView.BaseItemVM

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