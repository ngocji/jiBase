package com.jibase.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

open class ITextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : TextView(context, attributeSet, defStyle) {
    init {
        initView(attributeSet)
    }
    open fun initView(attrs: AttributeSet?) {
        // free implement
    }
}