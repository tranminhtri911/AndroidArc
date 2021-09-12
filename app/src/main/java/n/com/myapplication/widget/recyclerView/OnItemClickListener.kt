package n.com.myapplication.widget.recyclerView

import android.view.View
import n.com.myapplication.util.Constant.POSITION_DEFAULT

/**
 * OnItemClickListener
 *
 * @param <T> Data from item click
</T> */

interface OnItemClickListener<T> {
    fun onItemViewClick(item: T, position: Int = POSITION_DEFAULT, view: View? = null)
}