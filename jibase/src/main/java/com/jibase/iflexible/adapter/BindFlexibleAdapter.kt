package com.jibase.iflexible.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jibase.iflexible.items.interfaceItems.IFlexible

class BindFlexibleAdapter<T : IFlexible<*>>(private val liveData: LiveData<MutableList<T>>, hasStateId: Boolean = false) : FlexibleAdapter<T>(liveData.value
        ?: mutableListOf(), hasStateId), Observer<MutableList<T>> {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        liveData.observeForever(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        liveData.removeObserver(this)
    }

    override fun onChanged(list: MutableList<T>?) {
        val resultList = list ?: mutableListOf()
        updateDataSet(resultList)
    }
}