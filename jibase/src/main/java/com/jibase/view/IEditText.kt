package com.jibase.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

open class IEditText @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : EditText(context, attributeSet, defStyle) {

    init {
        initView(attributeSet)
    }

    open fun initView(attrs: AttributeSet?) {
        // free implement
    }
}