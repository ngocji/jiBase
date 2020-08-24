package com.jibinding.adapter.viewholder

import androidx.databinding.ViewDataBinding
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.viewholder.simple.FlexibleViewHolder

open class BindFlexibleViewHolder(val binding: ViewDataBinding, adapter: FlexibleAdapter<*>, isStickyHeader: Boolean = false) :
        FlexibleViewHolder(binding.root, adapter, isStickyHeader)