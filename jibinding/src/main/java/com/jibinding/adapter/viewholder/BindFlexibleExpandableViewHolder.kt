package com.jibinding.adapter.viewholder

import androidx.databinding.ViewDataBinding
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.viewholder.simple.FlexibleExpandableViewHolder

open class BindFlexibleExpandableViewHolder(val binding: ViewDataBinding, adapter: FlexibleAdapter<*>, isStickyHeader: Boolean = false) :
        FlexibleExpandableViewHolder(binding.root, adapter, isStickyHeader)