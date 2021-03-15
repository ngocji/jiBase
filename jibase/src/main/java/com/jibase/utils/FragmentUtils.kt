package com.jibase.utils

import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.jibase.helper.KeyboardHelper

object FragmentUtils {
    // region main feature
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun <T> ViewGroup.replace(target: T,
                              fragment: Fragment,
                              addToBackStack: Boolean = false,
                              @AnimRes enter: Int = ID_NULL,
                              @AnimRes exit: Int = ID_NULL,
                              @AnimRes popEnter: Int = ID_NULL,
                              @AnimRes popExit: Int = ID_NULL) {
        val fragmentManager = getFragmentManager(target)
        val tagName = getTag(fragment::class.java)
        // hide keyboard
        KeyboardHelper.hideKeyboard(target)
        fragmentManager.beginTransaction().run {
            // add to back stack if need
            if (addToBackStack) addToBackStack(tagName)

            setCustomAnimations(enter, exit, popEnter, popExit)
            replace(id, fragment, tagName)
            commitAllowingStateLoss()
        }
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun <T> remove(target: T, fragment: Fragment? = getCurrentFragment(target), hasAnimation: Boolean = true) {
        if (fragment == null) return
        val count = getCountOfBackStack(target)
        if (count <= 0) return

        val fragmentManager = getFragmentManager(target)
        val tag = getTag(fragment::class.java)
        KeyboardHelper.hideKeyboard(target)

        for (i in (count - 1) downTo 0) {
            fragmentManager.getBackStackEntryAt(i).also {
                if (it.name == tag) {
                    if (hasAnimation) {
                        fragmentManager.popBackStack()
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
                    } else {
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
                        fragmentManager.popBackStack()
                    }
                    return
                }
            }
        }
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun <T, F : Fragment> goto(target: T, fragmentCls: Class<F>, errorFragmentInBackStack: (() -> Unit)? = null, refreshUI: Boolean = false, hasAnimation: Boolean = true) {
        val count = getCountOfBackStack(target)
        if (count <= 0) return
        val fragmentManager = getFragmentManager(target)
        val tag = getTag(fragmentCls)
        KeyboardHelper.hideKeyboard(target)

        for (i in (count - 1) downTo 0) {
            fragmentManager.getBackStackEntryAt(i).also {
                if (it.name == tag) {
                    if (refreshUI) {
                        // todo check refresh ui
                    }
                    return
                } else {
                    remove(target, hasAnimation = hasAnimation)
                }
            }
        }

        errorFragmentInBackStack?.invoke()
    }

    // endregion

    // region get fragment
    @JvmStatic
    fun <T> getCurrentFragment(target: T): Fragment? {
        return getFragmentManager(target).fragments.lastOrNull()
    }


    @JvmStatic
    fun <T> getFragmentByTag(target: T, tag: String): Fragment? {
        return getFragmentManager(target).findFragmentByTag(tag)
    }

    @JvmStatic
    fun <T, F : Fragment> getFragmentByTag(target: T, cls: Class<F>): Fragment? {
        return getFragmentByTag(target, getTag(cls))
    }


    @JvmStatic
    fun <T> getPreviousFragment(target: T): Fragment? {
        val manager = getFragmentManager(target)
        val index = getCountOfBackStack(target) - 2
        return if (index >= 0) {
            getFragmentByTag(target, manager.getBackStackEntryAt(index).name ?: "")
        } else {
            null
        }
    }

    // endregion

    // region backStack
    @JvmStatic
    fun <T> removeAllFromBackStack(target: T, skipTag: String = "", action: (() -> Unit)? = null) {
        val fragmentManager = getFragmentManager(target)
        if (fragmentManager.isStateSaved) {
            action?.invoke()
            return
        }
        if (skipTag.isBlank()) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            val count = fragmentManager.backStackEntryCount
            if (count > 1) {
                for (i in (count - 1) downTo 0) {
                    fragmentManager.getBackStackEntryAt(i).also {
                        if (it.name != skipTag) {
                            fragmentManager.popBackStackImmediate()
                        }
                    }
                }
            }
        }
        action?.invoke()
    }


    @JvmStatic
    fun <T> popBackStack(target: T) {
        getFragmentManager(target).popBackStack()
    }

    // endregion

    // region helper method
    @JvmStatic
    fun <T> getCountOfBackStack(target: T): Int {
        return getFragmentManager(target).backStackEntryCount
    }

    @JvmStatic
    fun <T : Fragment> getTag(fragment: Class<T>): String {
        return fragment::class.java.name
    }

    private fun <T> getFragmentManager(target: T): FragmentManager {
        return when (target) {
            is Fragment -> target.childFragmentManager
            is FragmentActivity -> target.supportFragmentManager
            else -> throw IllegalArgumentException("Target is a fragment or a activity")
        }
    }
    // endregion
}