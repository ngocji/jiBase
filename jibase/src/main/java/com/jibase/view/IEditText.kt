package com.jibase.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

open class IEditText @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : AppCompatEditText(context, attributeSet, defStyle) {

    init {
        initView(attributeSet)
    }

    open fun initView(attrs: AttributeSet?) {
        // free implement
    }
}