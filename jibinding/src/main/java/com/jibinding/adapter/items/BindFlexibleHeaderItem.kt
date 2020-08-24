package com.jibinding.adapter.items

import android.view.ViewGroup
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleHeaderItem
import com.jibinding.adapter.viewholder.BindFlexibleViewHolder
import com.jibinding.helper.initBinding

abstract class BindFlexibleHeaderItem : AbstractFlexibleHeaderItem<BindFlexibleViewHolder>() {
    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): BindFlexibleViewHolder {
        return BindFlexibleViewHolder(parent.initBinding(getLayoutRes()), adapter, true)
    }
}