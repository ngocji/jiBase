package com.jibase.utils

import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.jibase.extensions.gone

/**
 * Create by Ngocji on 10/21/2018
 **/


object FragmentUtils {
    private var animIn = -1
    private var animOut = -1

    /**
     * Call initAnimation when custom animation
     * @param animIn: animation when fragment show
     * @param animOut: animation when fragment hide
     */
    fun initAnimation(@AnimRes animIn: Int, @AnimRes animOut: Int) {
        this.animIn = animIn
        this.animOut = animOut
    }

    /**
     * replace framgment
     * @param activity: activity
     * @param idContainer: id in layout want use replace fragment
     * @param addToBackStack: true-> addToBackstack, false-> no add
     * @param fragment: fragment
     */
    fun replace(
        activity: FragmentActivity,
        idContainer: Int,
        addToBackStack: Boolean,
        fragment: Fragment,
        customAnimIn: Int = -1,
        customAnimOut: Int = -1
    ) {
        val tagName = fragment::class.java.simpleName
        val trans = activity.supportFragmentManager.beginTransaction()
        // Add to back stack-->
        if (addToBackStack) trans.addToBackStack(tagName)

        // set custom animation when transition
        if (customAnimIn != -1 && customAnimOut != -1) {
            trans.setCustomAnimations(customAnimIn, customAnimOut)
        } else if (animIn != -1 && animOut != -1) {
            trans.setCustomAnimations(animIn, animOut)
        }

        // set fragment
        trans.replace(idContainer, fragment, tagName)

        // commit replace
        trans.commitAllowingStateLoss()
    }

    /**
     * Call initAnimation add list fragment when use show
     */
    fun initAddFragment(
        activity: FragmentActivity,
        idContainer: Int,
        addToBackStack: Boolean,
        vararg fragments: Fragment
    ) {
        val trans = activity.supportFragmentManager.beginTransaction()
        trans.setCustomAnimations(animIn, animOut)
        fragments.forEach { frag ->
            trans.add(idContainer, frag, frag::class.java.simpleName)
            if (addToBackStack) trans.addToBackStack(frag::class.java.simpleName)
        }
        trans.commitAllowingStateLoss()
    }

    /**
     * Show fragment
     * @param activity: activity
     * @param idContainer: id layout when use show fragment
     * @param fragment: fragment want show
     */
    fun show(activity: FragmentActivity, idContainer: Int, fragment: Fragment) {
        hideAllView(activity, idContainer)
        activity.supportFragmentManager.beginTransaction().apply {
            show(fragment)
            commitAllowingStateLoss()
        }
    }

    /**
     * get current showing fragment
     * @param activity: activity
     * @return fragment showing, null if  not fragment showing
     */
    fun getCurrentFragment(activity: FragmentActivity): Fragment? {
        return activity.supportFragmentManager.fragments.lastOrNull()
    }

    /**
     *  find fragment by tag in backstack
     *  @param activity: activity
     *  @param tag: tag fragment in backstack
     *  @return fragment, null if no fragment in backstack
     */
    fun getFragmentByTag(activity: FragmentActivity, tag: String): Fragment? {
        val manager = activity.supportFragmentManager
        return manager.findFragmentByTag(tag)
    }

    /**
     * find fragment by tag
     * @param activity: activity
     * @param clazz: class of fragmnet
     * @return fragment or null
     */
    fun getFragmentByTag(activity: FragmentActivity, clazz: Class<Fragment>): Fragment? {
        return getFragmentByTag(activity, clazz.simpleName)
    }

    /**
     * Get previous fragment in backstack
     * @param activity
     * @return fragment or null
     */
    fun getPreviousFragment(activity: FragmentActivity): Fragment? {
        val manager = activity.supportFragmentManager
        val count = manager.backStackEntryCount
        return if (count > 0) {
            getFragmentByTag(activity, manager.getBackStackEntryAt(count - 1).name ?: "")
        } else {
            null
        }
    }

    /**
     * call function when handle back stack fragment
     * @param activity: activity
     */
    fun handleBackFragment(activity: FragmentActivity): Fragment? {
        val manager = activity.supportFragmentManager
        if (manager.isStateSaved) return null

        val countStackEntry = manager.backStackEntryCount
        if (countStackEntry > 1) {
            manager.popBackStackImmediate()
        }
        return getCurrentFragment(activity)
    }

    /**
     * hide all fragment in  activity
     * @param activity: activity
     * @param idContainer: id in layout when hide all fragment
     */
    private fun hideAllView(activity: FragmentActivity, idContainer: Int) {
        val viewGroup = activity.findViewById<ViewGroup>(idContainer)
        for (i in 0 until viewGroup.childCount) {
            viewGroup.getChildAt(i).gone()
        }
    }


    /**
     * Remove all fragment from backStack
     * @param activity
     * @param skipTag:  tag of fragment you want keep
     * @param action: action when remove finsish
     */
    fun removeAllFromBackStack(
        activity: FragmentActivity,
        skipTag: String = "",
        action: (() -> Unit)? = null
    ) {
        val fragmentManager = activity.supportFragmentManager
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

    fun getCountOfBackStack(fragmentActivity: FragmentActivity): Int {
        return fragmentActivity.supportFragmentManager.backStackEntryCount
    }
}