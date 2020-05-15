package com.jibase.iflexible.items.binding

import android.view.ViewGroup
import com.jibase.extensions.initBinding
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleHeaderItem
import com.jibase.iflexible.viewholder.binding.BindFlexibleViewHolder

abstract class BindFlexibleHeaderItem : AbstractFlexibleHeaderItem<BindFlexibleViewHolder>(){
    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): BindFlexibleViewHolder {
        return BindFlexibleViewHolder(parent.initBinding(getLayoutRes()), adapter, true)
    }
}