package com.jibase.iflexible.listener

import android.view.View

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int): Boolean
}