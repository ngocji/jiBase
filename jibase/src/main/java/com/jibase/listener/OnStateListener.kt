package com.jibase.listener

import android.view.View

interface OnStateListener {
    fun onStateClicked(v: View, state: String)
}