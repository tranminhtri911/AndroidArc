package com.example.mvvmkoin.screen.main.userPaging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mvvmkoin.R
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.util.GlideApp
import kotlinx.android.synthetic.main.fragment_paging.*
import kotlinx.android.synthetic.main.fragment_paging.view.*


class PagingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_paging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getParcelable(USER_EXTRA) as User?

        view.label.text = user?.fullName

        view.sublabel.text = user?.name

        val url = user?.owner?.avatarUrl

        GlideApp.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(background)

    }

    companion object {

        private const val USER_EXTRA = "USER_EXTRA"

        fun newInstance(user: User) = PagingFragment().apply {
            val bundle = Bundle().apply { putParcelable(USER_EXTRA, user) }
            arguments = bundle
        }
    }
}

