
package com.example.mvvmkoin.util.extension

import android.content.Intent
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mvvmkoin.R

/**
 * Various extension functions for AppCompatActivity.
 */

fun AppCompatActivity.startActivity(@NonNull intent: Intent,
        flags: Int? = null) {
    flags.notNull {
        intent.flags = it
    }
    startActivity(intent)
}

fun AppCompatActivity.startActivityForResult(@NonNull intent: Intent,
        requestCode: Int, flags: Int? = null) {
    flags.notNull {
        intent.flags = it
    }
    startActivityForResult(intent, requestCode)
}

fun AppCompatActivity.replaceFragmentInActivity(@IdRes containerId: Int, fragment: Fragment,
        addToBackStack: Boolean = true, tag: String = fragment::class.java.simpleName) {
    supportFragmentManager.transact {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }
}

fun AppCompatActivity.addFragmentToActivity(@IdRes containerId: Int, fragment: Fragment,
        addToBackStack: Boolean = true, tag: String = fragment::class.java.simpleName) {
    supportFragmentManager.transact {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }
}

fun AppCompatActivity.goBackFragment(): Boolean {
    val isShowPreviousPage = supportFragmentManager.backStackEntryCount > 0
    if (isShowPreviousPage) {
        supportFragmentManager.popBackStackImmediate()
    }
    return isShowPreviousPage
}
