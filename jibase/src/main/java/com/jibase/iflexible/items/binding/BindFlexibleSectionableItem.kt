package com.jibase.iflexible.items.binding

import android.view.ViewGroup
import com.jibase.extensions.initBinding
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleSectionableItem
import com.jibase.iflexible.items.interfaceItems.IHeader
import com.jibase.iflexible.viewholder.binding.BindFlexibleViewHolder

abstract class BindFlexibleSectionableItem<H : IHeader<*>> : AbstractFlexibleSectionableItem<BindFlexibleViewHolder, H>() {
    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): BindFlexibleViewHolder {
        return BindFlexibleViewHolder(parent.initBinding(getLayoutRes()), adapter)
    }
}