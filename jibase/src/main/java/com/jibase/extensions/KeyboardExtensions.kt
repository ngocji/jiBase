@file:JvmName("KeyboardExtensions")

package com.jibase.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment


fun EditText.showKeyboard() {
    val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.showSoftInput(this, 0)
}

fun EditText.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val currentFocus = activity?.currentFocus ?: View(requireContext())
    val inputMethodManager =
        context?.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}

fun Activity.hideKeyboard() {
    val currentFocus = currentFocus ?: View(this)
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}