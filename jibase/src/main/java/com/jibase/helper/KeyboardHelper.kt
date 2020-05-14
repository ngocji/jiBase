package com.jibase.helper

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jibase.listener.OnKeyboardListener
import org.koin.core.KoinComponent

class KeyboardHelper(private val context: Context) : KoinComponent {

    fun showKeyboard(target: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(target, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun <T> hideKeyboardInternal(target: T) {
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

    fun addListener(target: View, callback: OnKeyboardListener) {
        target.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                target.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val rect = Rect()
                target.getWindowVisibleDisplayFrame(rect)
                val screenHeight = context.resources.displayMetrics.heightPixels
                val keyboardHeight = screenHeight - rect.bottom
                if (keyboardHeight > 100) {
                    callback.onKeyboardVisible()
                    callback.onKeyboardChangeHeight(keyboardHeight)
                } else {
                    callback.onKeyboardHide()
                }
                target.viewTreeObserver.addOnGlobalLayoutListener(this)
            }
        })
    }

    private fun hideKeyboardInternal(focusView: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                    focusView.windowToken, 0)
        } catch (ex: Exception) {
        }
    }
}