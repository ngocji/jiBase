@file:JvmName("NavControllerExtension")

package com.jibase.extensions

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import com.jibase.utils.Log

fun NavController.safeNavigate(directions: NavDirections): Boolean =
    try {
        navigate(directions)
        true
    } catch (e: IllegalArgumentException) {
        Log.e("Error safeNavigate to ${directions.actionId}")
        false
    }

fun NavController.safeNavigate(directions: Int): Boolean =
    try {
        navigate(directions)
        true
    } catch (e: IllegalArgumentException) {
        Log.e("Error safeNavigate to $directions")
        false
    }

fun NavController.safeNavigate(directions: Int, bundle: Bundle): Boolean =
    try {
        navigate(directions, bundle)
        true
    } catch (e: IllegalArgumentException) {
        Log.e("Error safeNavigate to $directions")
        false
    }

fun NavController.safeNavigate(uri: Uri): Boolean =
    try {
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        navigate(request)
        true
    } catch (e: IllegalArgumentException) {
        Log.e("Error safeNavigate to $uri")
        false
    }

fun NavController.safeNavigate(directions: Int, bundle: Bundle, navOptions: NavOptions): Boolean =
    try {
        navigate(directions, bundle, navOptions)
        true
    } catch (e: IllegalArgumentException) {
        Log.e("Error safeNavigate to $directions")
        false
    }


fun NavController.hasBackStack(actionId: Int): Boolean {
    return try {
        getBackStackEntry(actionId)
        true
    } catch (e: Exception) {
        false
    }
}

fun NavController.isDestinationVisible(actionId: Int): Boolean {
    return actionId == this.currentDestination?.id
}

fun Fragment.findNavController(viewId: Int) = Navigation.findNavController(
    requireActivity().findViewById(viewId) ?: throw NullPointerException("Fragment is null view")
)

fun Fragment.findParentNavController(step: Int): NavController? {
    if (step == 0) throw IllegalStateException("Step must > 0")
    var index = 0
    var parentFragment:Fragment? = this
    while (index < step) {
        parentFragment = parentFragment?.parentFragment?.parentFragment
        index++
    }

    return parentFragment?.findNavController()
}