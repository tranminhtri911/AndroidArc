package com.example.flowmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.example.flowmvvm.data.source.remote.api.error.ErrorResponse
import com.example.flowmvvm.utils.LogUtils
import com.example.flowmvvm.utils.extension.createViewModelLazy
import com.example.flowmvvm.utils.extension.notNull
import com.example.flowmvvm.utils.extension.showToast
import com.example.flowmvvm.widgets.dialogManager.DialogManager
import com.example.flowmvvm.widgets.dialogManager.DialogManagerImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModelClass: KClass<VM>
    val viewModel: VM by createViewModelLazy(viewModelClass)

    protected abstract val bindingInflater: (LayoutInflater) -> B
    protected val binding: B by lazy { bindingInflater.invoke(layoutInflater) }

    protected val coroutineScope: LifecycleCoroutineScope
        get() = lifecycleScope

    private lateinit var dialogManager: DialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dialogManager = DialogManagerImpl(this)
        bindViewModel()
        setupView()
        bindView()
    }

    override fun onDestroy() {
        dialogManager.onRelease()
        super.onDestroy()
    }

    private fun bindViewModel() {
        viewModel.tag = viewModelClass.simpleName.toString()
        launchStart { viewModel.loadingEvent.collectLatest { setLoading(it) } }
        launchStart { viewModel.errorEvent.collectLatest { it?.let { onHandleError(it) } } }
    }

    fun setLoading(isShow: Boolean) {
        LogUtils.d("setLoading", "${viewModelClass.simpleName}: $isShow)")
        if (isShow) dialogManager.showLoading() else dialogManager.hideLoading()
    }

    fun onHandleError(throwable: Throwable) {
        LogUtils.e("onHandleError", "${viewModelClass.simpleName}: ${throwable.message})")

        val error = ErrorResponse.convertToRetrofitException(throwable)

        error.getMessageError().notNull {
            showToast(it)
        }
    }

    fun launchStart(block: suspend () -> Unit) {
        with(lifecycle) {
            coroutineScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    block.invoke()
                }
            }
        }
    }

    protected abstract fun setupView()

    protected abstract fun bindView()
}