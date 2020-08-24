package com.jibinding.adapter.items

import android.view.ViewGroup
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleItem
import com.jibinding.adapter.viewholder.BindFlexibleViewHolder
import com.jibinding.helper.initBinding

abstract class BindFlexibleItem : AbstractFlexibleItem<BindFlexibleViewHolder>() {
    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): BindFlexibleViewHolder {
        return com.jibinding.adapter.viewholder.BindFlexibleViewHolder(
                parent.initBinding(getLayoutRes()), adapter
        )
    }
}