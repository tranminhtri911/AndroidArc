package n.com.myapplication.util.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

fun ViewGroup.inflateBinding(resLayoutID: Int, attachToParent: Boolean = false): ViewDataBinding {
    return DataBindingUtil.inflate(LayoutInflater.from(this.context), resLayoutID,
            this, attachToParent)
}