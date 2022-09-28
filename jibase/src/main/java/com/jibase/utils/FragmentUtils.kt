package com.jibase.utils

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.jibase.R
import com.jibase.extensions.getTagName


object FragmentUtils {
    fun <T : Any> replace(option: ReplaceOption<T>?) {
        option ?: return
        val transaction = getFragmentManager(option.target)
            .beginTransaction()

        val tagName = option.fragment.getTagName()

        if (option.popWhenExists && getFragmentByTag(option.target, tagName) != null) {
            popBackTo(option.target, tagName)
            return
        }

        //Add to back stack-->
        if (option.addToBackStack) transaction.addToBackStack(tagName)


        transaction.setCustomAnimations(option.enter, option.exit, option.popEnter, option.popExit)

        //set fragment
        transaction.replace(option.containerId, option.fragment, tagName)

        //commit replace
        when {
            option.commitNow -> transaction.commitNowAllowingStateLoss()
            else -> transaction.commitAllowingStateLoss()
        }
    }


    fun <T : Any> getCurrentFragment(target: T): Fragment? {
        return getFragmentManager(target).fragments.lastOrNull()
    }


    fun <T : Any> getFragmentByTag(target: T, tagName: String): Fragment? {
        return getFragmentManager(target).findFragmentByTag(tagName)
    }

    fun <T : Any> isFirstFragment(target: T): Boolean {
        return getCountOfBackStack(target) <= 1
    }

    fun <T : Any> getPreviousFragment(target: T): Fragment? {
        val manager = getFragmentManager(target)
        val index = manager.backStackEntryCount - 2
        return if (index >= 0) {
            getFragmentByTag(target, manager.getBackStackEntryAt(index).name ?: "")
        } else {
            null
        }
    }

    fun <T : Any> getCountOfBackStack(target: T): Int {
        return getFragmentManager(target).backStackEntryCount
    }

    fun <T : Any> popBackTo(target: T, tagName: String, inclusive: Boolean = false) {
        val fragmentManager = getFragmentManager(target)
        if (fragmentManager.isStateSaved) {
            return
        }
        fragmentManager.popBackStack(tagName, if (inclusive) POP_BACK_STACK_INCLUSIVE else 0)
    }

    fun <T : Any> popBack(target: T) {
        val fragmentManager = getFragmentManager(target)
        if (fragmentManager.isStateSaved) {
            return
        }

        fragmentManager.popBackStack()
    }

    private fun <T> getFragmentManager(target: T?): FragmentManager {
        return when (target) {
            is FragmentActivity -> target.supportFragmentManager
            is Fragment -> target.childFragmentManager
            is FragmentManager -> target
            else -> throw IllegalArgumentException("Target is activity or fragment or fragmentManager!")
        }
    }


    class ReplaceOption<T : Any> {
        lateinit var target: T
        lateinit var fragment: Fragment

        var containerId: Int = -1
        var addToBackStack = true
        var enter: Int = R.anim.idle
        var exit: Int = R.anim.idle
        var popEnter: Int = R.anim.idle
        var popExit: Int = R.anim.idle
        var commitNow: Boolean = false
        var popWhenExists: Boolean = false

        fun with(target: T): ReplaceOption<T> {
            this.target = target
            return this
        }

        fun setContainerId(@IdRes id: Int): ReplaceOption<T> {
            this.containerId = id
            return this
        }

        fun addToBackStack(add: Boolean): ReplaceOption<T> {
            this.addToBackStack = add
            return this
        }

        fun setFragment(fragment: Fragment): ReplaceOption<T> {
            this.fragment = fragment
            return this
        }

        fun setCommitNow(commit: Boolean): ReplaceOption<T> {
            this.commitNow = commit
            return this
        }

        fun setPopWhenExists(pop: Boolean): ReplaceOption<T> {
            this.popWhenExists = pop
            return this
        }

        fun setAnimation(
            @AnimRes enter: Int = R.anim.idle,
            @AnimRes exit: Int = R.anim.idle,
            @AnimRes popEnter: Int = R.anim.idle,
            @AnimRes popExit: Int = R.anim.idle
        ): ReplaceOption<T> {
            this.enter = enter
            this.exit = exit
            this.popEnter = popEnter
            this.popExit = popExit
            return this
        }
    }
}