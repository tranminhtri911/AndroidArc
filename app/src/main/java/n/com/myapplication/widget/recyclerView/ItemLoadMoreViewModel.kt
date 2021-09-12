package n.com.myapplication.widget.recyclerView

import android.view.View
import kotlinx.android.synthetic.main.item_load_more.view.*

class ItemLoadMoreViewModel : BaseItemVM<Boolean>() {

    override fun onItemClicked(view: View) {
        //No-Op
    }

    override fun bindData(data: Boolean?) {
        binding?.root?.progress?.visibility = if (data == true) View.VISIBLE else View.GONE
    }
}