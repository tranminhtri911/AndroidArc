package com.example.mvvmkoin.util.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mvvmkoin.R
import com.example.mvvmkoin.util.TransitionHelper
import com.example.mvvmkoin.util.TransitionType
import com.example.mvvmkoin.util.navigation.NavAnimateType

fun Fragment.replaceFragment(@IdRes containerId: Int, fragment: Fragment,
        addToBackStack: Boolean = true, tag: String = fragment::class.java.simpleName) {
    fragmentManager?.transact {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }
}

fun Fragment.addFragment(fragment: Fragment, addToBackStack: Boolean = true,
        tag: String = fragment::class.java.simpleName) {
    fragmentManager?.transact {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(fragment, tag)
    }
}

fun Fragment.goBackFragment(): Boolean {
    fragmentManager.notNull {
        val isShowPreviousPage = it.backStackEntryCount > 0
        if (isShowPreviousPage) {
            it.popBackStackImmediate()
        }
        return isShowPreviousPage
    }
    return false
}

fun Fragment.generateTag(): String {
    return this::class.java.simpleName
}

fun FragmentManager.isExitFragment(tag: String): Boolean {
    return this.findFragmentByTag(tag) != null
}

fun FragmentTransaction.setAnimations(animateType: NavAnimateType) {
    when (animateType) {
        NavAnimateType.FADE -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        NavAnimateType.SLIDE_DOWN -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        NavAnimateType.SLIDE_UP -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        NavAnimateType.SLIDE_LEFT -> {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, 0, 0)
        }
        NavAnimateType.SLIDE_RIGHT -> {
            setCustomAnimations(R.anim.slide_in_right,  0,0, R.anim.slide_out_right)
        }
    }
}


/**
 * Runs a FragmentTransaction, then calls commitAllowingStateLoss().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}




