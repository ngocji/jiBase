package com.jibase.iflexible.items.binding

import android.view.ViewGroup
import com.jibase.extensions.initBinding
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleExpandItem
import com.jibase.iflexible.items.interfaceItems.IFlexible
import com.jibase.iflexible.viewholder.binding.BindFlexibleExpandableViewHolder

abstract class BindFlexibleExpandableItem<S : IFlexible<*>> : AbstractFlexibleExpandItem<BindFlexibleExpandableViewHolder, S>() {
    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): BindFlexibleExpandableViewHolder {
        return BindFlexibleExpandableViewHolder(parent.initBinding(getLayoutRes()), adapter, false)
    }
}