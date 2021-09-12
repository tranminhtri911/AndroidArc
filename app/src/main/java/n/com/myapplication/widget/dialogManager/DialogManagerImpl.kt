package n.com.myapplication.widget.dialogManager

import android.content.Context
import n.com.myapplication.util.Constant
import n.com.myapplication.util.LogUtils
import n.com.myapplication.util.extension.notNull
import java.lang.ref.WeakReference

class DialogManagerImpl(ctx: Context?) : DialogManager {

    private var context: WeakReference<Context?> = WeakReference(ctx)

    private var progressDialog: ProgressDialog? = null

    init {
        context.get().notNull { progressDialog = ProgressDialog(it) }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun showProcessing() {
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }

    override fun onRelease() {
        progressDialog = null
        LogUtils.d(TAG, Constant.RELEASED)
    }

    companion object {
        const val TAG = "DialogManagerImpl"
    }

}