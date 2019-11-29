package com.jibase.listener

import android.view.View

/**
 * Create by Ngocji on 11/24/2018
 **/


interface OnStateListener {
    fun onStateClicked(v: View, state: String)
}