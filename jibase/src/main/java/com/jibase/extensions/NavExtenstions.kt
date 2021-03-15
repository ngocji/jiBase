package com.jibase.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController


fun NavController.hasBackStack(actionId: Int): Boolean {
    return try {
        getBackStackEntry(actionId)
        true
    } catch (e: Exception) {
        false
    }
}

fun Fragment.findNavController(viewId: Int): NavController {
    return requireActivity().findNavController(viewId)
}

fun FragmentActivity.findNavController(viewId: Int): NavController {
    return Navigation.findNavController(this, viewId)
}

fun FragmentActivity.getCurrentFragment(viewId: Int): Fragment? {
    return supportFragmentManager.findFragmentById(viewId)?.childFragmentManager?.fragments?.firstOrNull()
}

fun Fragment.getCurrentFragment(viewId: Int): Fragment? {
    return activity?.getCurrentFragment(viewId)
}

fun FragmentActivity.getCountOfBackStack(viewId: Int): Int {
    return supportFragmentManager.findFragmentById(viewId)?.childFragmentManager?.backStackEntryCount
        ?: 0
}