package com.jibase.listener

abstract class OnKeyboardListener {
    fun onKeyboardVisible() {}
    fun onKeyboardHide() {}
    fun onKeyboardChangeHeight(height: Int) {}
}