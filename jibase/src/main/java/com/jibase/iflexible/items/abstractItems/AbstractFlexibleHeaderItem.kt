package com.jibase.iflexible.items.abstractItems

import com.jibase.iflexible.items.interfaceItems.IHeader
import com.jibase.iflexible.viewholder.simple.FlexibleViewHolder

abstract class AbstractFlexibleHeaderItem<VH : FlexibleViewHolder> : AbstractFlexibleItem<VH>(), IHeader<VH> {
    init {
        setHidden(true)
    }
}