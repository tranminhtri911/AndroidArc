package com.example.mvvmkoin.screen.userDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.Observer
import com.example.mvvmkoin.R
import com.example.mvvmkoin.base.BaseActivity
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.databinding.ActivityUserDetailBinding
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.util.*
import kotlin.reflect.KClass

class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>() {


    override val layoutID: Int
        get() = R.layout.activity_user_detail

    override val viewModelClass: KClass<UserDetailViewModel>
        get() = UserDetailViewModel::class

    override fun bindViewInput() {

        enterTransition()

        floatBtn.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val v = binding.body
                val x: Int
                val y: Int
                val c = when {
                    Random().nextInt() % 2 == 0 -> {
                        x = binding.body.width
                        y = binding.body.height
                        R.color.colorTest1
                    }
                    Random().nextInt() % 3 == 0 -> {
                        x = binding.body.width / 2
                        y = 0
                        R.color.colorTest2
                    }
                    else -> {
                        x = binding.body.width / 2
                        y = binding.body.width / 2
                        R.color.colorTest3
                    }
                }
                animateRevealColorFromCoordinates(v, c, x, y)
            }
            abc.setTextColor(resources.getColor(R.color.text))
            return@setOnTouchListener false
        }

        val user = intent.getParcelableExtra<User>(USER_EXTRA) ?: return
        viewModel.setUser(user)
    }

    override fun bindViewOutput() {
        viewModel.actionBack.observe(this, Observer {
            returnTransition()
        })
    }

    companion object {

        private const val USER_EXTRA = "USER_EXTRA"

        fun getInstance(context: Context?, user: User): Intent? {
            context?.let {
                return Intent(context, UserDetailActivity::class.java).apply {
                    val bundle = Bundle().apply { putParcelable(USER_EXTRA, user) }
                    putExtras(bundle)
                }
            } ?: run {
                return null
            }
        }
    }
}
