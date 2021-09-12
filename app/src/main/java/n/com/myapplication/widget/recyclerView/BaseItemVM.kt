package n.com.myapplication.widget.recyclerView

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ViewDataBinding
import n.com.myapplication.util.Constant

abstract class BaseItemVM<T>
constructor(
        var binding: ViewDataBinding? = null,
        var listener: OnItemClickListener<T>? = null,
        var position: Int = Constant.POSITION_DEFAULT) : BaseObservable() {

    abstract fun bindData(data: T?)

    abstract fun onItemClicked(view: View)
}
