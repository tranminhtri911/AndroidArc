package com.example.mvvmkoin.util.navigation

import androidx.fragment.app.Fragment

interface NavHelper {

    fun addFrag(fragment: Fragment,
            addToBackStack: Boolean = true,
            animateType: NavAnimateType = NavAnimateType.FADE,
            tag: String = fragment::class.java.simpleName)

    fun replaceFrag(fragment: Fragment,
            addToBackStack: Boolean = true,
            animateType: NavAnimateType = NavAnimateType.FADE,
            tag: String = fragment::class.java.simpleName)


    fun switchTab(tab: Int,
            fragment: Fragment,
            isAddFrag: Boolean = false,
            addToBackStack: Boolean = true,
            animateType: NavAnimateType = NavAnimateType.FADE,
            tag: String = fragment::class.java.simpleName)

    fun getFragment(position: Int): Fragment?

    fun getCurrentFrag(): Fragment

    fun getCurrentTab(): Int

    fun popBack(isToRoot: Boolean = false)

    fun isCanPopBack(): Boolean
}