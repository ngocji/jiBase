package com.jibinding.adapter.items

import android.view.ViewGroup
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleExpandItem
import com.jibase.iflexible.items.interfaceItems.IFlexible
import com.jibinding.adapter.viewholder.BindFlexibleExpandableViewHolder
import com.jibinding.helper.initBinding

abstract class BindFlexibleExpandableItem<S : IFlexible<*>> : AbstractFlexibleExpandItem<BindFlexibleExpandableViewHolder, S>() {
    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): BindFlexibleExpandableViewHolder {
        return BindFlexibleExpandableViewHolder(parent.initBinding(getLayoutRes()), adapter, false)
    }
}