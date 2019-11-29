package com.jibase.iflexible.helpers

import androidx.recyclerview.widget.DiffUtil
import com.jibase.iflexible.items.interfaceItems.IFlexible

open class FlexibleDiffCallback<T : IFlexible<*>>(private var oldList: List<T>, var newList: List<T>) : DiffUtil.Callback() {

    fun setItems(oldList: List<T>, newList: List<T>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getIdView() == newList[newItemPosition].getIdView()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}