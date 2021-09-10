package com.example.flowmvvm.widgets.dialogManager

import android.content.Context
import com.example.flowmvvm.utils.Constants
import com.example.flowmvvm.utils.LogUtils
import com.example.flowmvvm.utils.extension.notNull

import java.lang.ref.WeakReference

class DialogManagerImpl(ctx: Context?) : DialogManager {

    private var context: WeakReference<Context?> = WeakReference(ctx)

    private var progressDialog: ProgressDialog? = null

    init {
        context.get().notNull { progressDialog = ProgressDialog(it) }
    }

    override fun showLoading() {
        if (progressDialog != null && progressDialog?.isShowing == true) {
            return
        }
        context.get().notNull {
            progressDialog = ProgressDialog(it)
            progressDialog?.show()
        }
    }

    override fun showProcessing() {
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun onRelease() {
        progressDialog = null
        LogUtils.d(TAG, Constants.RELEASED)
    }

    companion object {
        const val TAG = "DialogManagerImpl"
    }

}