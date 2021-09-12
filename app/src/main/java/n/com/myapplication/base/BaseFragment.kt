package n.com.myapplication.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import n.com.myapplication.BR
import n.com.myapplication.data.source.remote.api.error.RetrofitException
import n.com.myapplication.di.Injectable
import n.com.myapplication.util.liveData.autoCleared
import n.com.myapplication.widget.dialogManager.DialogManager
import n.com.myapplication.widget.dialogManager.DialogManagerImpl
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract val layoutID: Int
    protected var binding by autoCleared<B>()

    protected abstract val viewModelClass: Class<VM>
    protected val viewModel: VM by lazy {
        ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    protected var dialogManager by autoCleared<DialogManager>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
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