package com.jibase.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.jibase.R

class IViewpager @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : ViewPager(context, attributeSet) {
    private var isEnableSwipe: Boolean = true

    init {
        initView(attributeSet)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.isEnableSwipe) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.isEnableSwipe) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    private fun initView(attributeSet: AttributeSet?) {
        attributeSet?.also {
            val a = context.obtainStyledAttributes(attributeSet, R.styleable
                    .IViewpager, 0, 0)

            isEnableSwipe = a.getBoolean(R.styleable.IViewpager_isEnableSwipe, true)

            a.recycle()
        }
    }

    // true if we can scroll (not locked)
    // false if we cannot scroll (locked)

    fun setPagingEnableSwipe(enabled: Boolean) {
        this.isEnableSwipe = enabled
    }
}