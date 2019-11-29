package com.jibase.iflexible.listener

import androidx.recyclerview.widget.RecyclerView

interface OnActionStateListener{
     fun onActionStateChanged(viewHolder: RecyclerView.ViewHolder, actionState: Int)
}