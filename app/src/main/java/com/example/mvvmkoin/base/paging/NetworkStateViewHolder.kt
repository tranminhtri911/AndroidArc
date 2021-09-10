package com.example.mvvmkoin.base.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkoin.R
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val progress = view.progress

    fun bindTo(networkState: NetworkState?) {

        progress.visibility = toVisibility(networkState?.status == Status.LOADING)

    }

    companion object {

        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_network_state, parent, false)
            return NetworkStateViewHolder(view)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) View.VISIBLE else View.GONE

        }
    }

}