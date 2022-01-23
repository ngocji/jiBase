@file:JvmName("KeyboardHelper")

package com.jibase.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object KeyboardHelper {
    @JvmStatic
    fun showKeyboard(target: View) {
        val imm =
            target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(target, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    @JvmStatic
    fun <T> hideKeyboard(target: T) {
        when (target) {
            is View -> {
                hideKeyboardInternal(target)
            }
            is Fragment -> {
                target.activity?.currentFocus?.also { focusView -> hideKeyboardInternal(focusView) }
            }
            is FragmentActivity -> {
                target.currentFocus?.also { focusView -> hideKeyboardInternal(focusView) }
            }
        }
    }

    private fun hideKeyboardInternal(focusView: View) {
        try {
            val imm =
                focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                focusView.windowToken, 0
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}