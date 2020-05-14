package com.jibase.listener

open class OnKeyboardListener {
    open fun onKeyboardVisible() {}
    open fun onKeyboardHide() {}
    open fun onKeyboardChangeHeight(height: Int) {}
}