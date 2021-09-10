package com.example.mvvmkoin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.mvvmkoin.BR
import com.example.mvvmkoin.data.source.remote.api.error.RetrofitException
import com.example.mvvmkoin.util.liveData.autoCleared
import com.example.mvvmkoin.widget.dialogManager.DialogManager
import com.example.mvvmkoin.widget.dialogManager.DialogManagerImpl
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>() : Fragment() {


    protected abstract val layoutID: Int
    protected var binding by autoCleared<B>()

    protected abstract val viewModelClass: KClass<VM>
    protected open val viewModel: VM by lazy { getViewModel(viewModelClass) }

    protected var dialogManager by autoCleared<DialogManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutID, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogManager = DialogManagerImpl(context)
        bindViewInput()
        bindViewOutput()
    }

    override fun onDestroyView() {
        dialogManager.onRelease()
        super.onDestroyView()
    }

    fun onHandleError(error: RetrofitException?) {
        if (context is BaseActivity<*, *>) {
            (context as BaseActivity<*, *>).onHandleError(error)
        }
    }

    fun transitionTo(intent: Intent) {
        val baseActivity = activity as BaseActivity<*, *>
        baseActivity.transitionTo(intent)
    }

    protected abstract fun bindViewInput()

    protected abstract fun bindViewOutput()
}