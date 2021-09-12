package n.com.myapplication.util.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import n.com.myapplication.R
import n.com.myapplication.util.LogUtils
import n.com.myapplication.util.extension.isExitFragment
import n.com.myapplication.util.extension.setAnimations
import n.com.myapplication.util.extension.transact

@Suppress("DEPRECATION")
class ContainerFragment : Fragment() {

    private var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentTab = arguments?.getInt(EXTRA_TAB) ?: return
    }

    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isAdded) {
            return
        }
        val fragment = childFragmentManager.findFragmentById(containerViewId) ?: return
        fragment.userVisibleHint = isVisibleToUser
    }

    fun popBack(isToRoot: Boolean) {
        val fragManager = childFragmentManager
        val backStackCount = fragManager.backStackEntryCount
        if (isToRoot) {
            for (i in 0 until backStackCount) {
                val backStackId = fragManager.getBackStackEntryAt(i).id
                fragManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } else {
            val isShowPrevious = backStackCount > 1
            if (isShowPrevious) {
                fragManager.popBackStackImmediate()
            }
        }
    }

    fun isCanPopBack(): Boolean {
        val isShowPrevious = childFragmentManager.backStackEntryCount > 1
        if (isShowPrevious) {
            childFragmentManager.popBackStackImmediate()
        }
        return isShowPrevious
    }

    fun addOrReplaceFrag(fragment: Fragment,
            isAddFrag: Boolean, addToBackStack: Boolean,
            animateType: NavAnimateType, tag: String) {

        with(childFragmentManager) {
            val isExits = isExitFragment(tag = tag)
            LogUtils.d(TAG, "$tag, isExits = $isExits, isAddFrag = $isAddFrag")
            if (isExits) return

            transact {
                setAnimations(animateType = animateType)

                if (addToBackStack) {
                    addToBackStack(tag)
                }

                if (isAddFrag) {
                    add(containerViewId, fragment, tag)
                } else {
                    replace(containerViewId, fragment, tag)
                }
            }
        }

    }


    companion object {

        private const val TAG = "ContainerFragment"

        private const val EXTRA_TAB = "EXTRA_TAB"

        const val containerViewId = R.id.layoutContainer

        fun newInstance(tab: Int) = ContainerFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_TAB, tab)
            }
        }

    }

}