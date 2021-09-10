package com.example.mvvmkoin.base

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mvvmkoin.BR
import com.example.mvvmkoin.data.source.remote.api.error.RetrofitException
import com.example.mvvmkoin.util.TransitionHelper
import com.example.mvvmkoin.util.TransitionType
import com.example.mvvmkoin.util.extension.notNull
import com.example.mvvmkoin.util.extension.showToast
import com.example.mvvmkoin.widget.dialogManager.DialogManager
import com.example.mvvmkoin.widget.dialogManager.DialogManagerImpl
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.math.hypot
import kotlin.reflect.KClass

@SuppressLint("Registered")
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModelClass: KClass<VM>
    protected open val viewModel: VM by lazy { getViewModel(viewModelClass) }

    protected abstract val layoutID: Int
    protected lateinit var binding: B

    protected var dialogManager: DialogManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutID)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        dialogManager = DialogManagerImpl(this)
        bindViewInput()
        bindViewOutput()
    }

    override fun onDestroy() {
        dialogManager?.onRelease()
        dialogManager = null
        super.onDestroy()
    }

    fun onHandleError(error: RetrofitException?) {
        error?.getMessageError().notNull { showToast(it) }
    }

    fun transitionTo(intent: Intent) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(this, true)
        val transitionOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
        startActivity(intent, transitionOptions.toBundle())
    }

    fun enterTransition(transition: Transition? = TransitionHelper.create(TransitionType.EXPLODE)) {
        window.enterTransition = transition
    }

    fun returnTransition(transition: Transition? = TransitionHelper.create(TransitionType.FADE)) {
        window.returnTransition = transition
        finishAfterTransition()
    }

    /**
     * Create Animator
     * @param view is ViewRoot
     * @param color is Background Color
     * @param x is MotionEvent.rawX, View.With
     * @param y is MotionEvent.rawY, View.Height
     * @return A Animator.
     */
    fun animateRevealColorFromCoordinates(view: ViewGroup, color: Int, x: Int, y: Int): Animator {
        val finalRadius = hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(view, x, y, 0f, finalRadius)
        view.setBackgroundColor(ContextCompat.getColor(this, color))
        anim.setDuration(500)
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.start()
        return anim
    }

    protected abstract fun bindViewInput()

    protected abstract fun bindViewOutput()

}