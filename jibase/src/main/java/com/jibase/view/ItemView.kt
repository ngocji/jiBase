package com.jibase.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import com.jibase.R
import com.jibase.extensions.gone
import com.jibase.extensions.inflate
import com.jibase.extensions.visible
import com.jibase.utils.ResourceUtils
import kotlinx.android.synthetic.main.layout_item_view.view.*

open class ItemView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle) {
    init {
        initView(attributeSet)
    }

    open fun initView(attrs: AttributeSet?) {
        inflate(R.layout.layout_item_view, true)

        attrs?.also {
            val a = context.resources.obtainAttributes(it, R.styleable.ItemView)

            // for icon
            val icon = a.getDrawable(R.styleable.ItemView_itemIcon)
            val iconColor = a.getColor(R.styleable.ItemView_itemIconColor, 0)
            val iconSize = a.getDimensionPixelSize(R.styleable.ItemView_itemIconSize, -1)
            setIcon(icon, iconColor, iconSize)

            // for text
            val text = a.getString(R.styleable.ItemView_itemText)
            val textColor = a.getColor(R.styleable.ItemView_itemTextColor, 0)
            val textSize = a.getDimension(R.styleable.ItemView_isTextOnly, -1f)
            setText(text, textColor, textSize)

            // for description
            val description = a.getString(R.styleable.ItemView_itemDescription)
            val descriptionColor = a.getColor(R.styleable.ItemView_itemDescriptionColor, 0)
            val descriptionSize = a.getDimension(R.styleable.ItemView_itemDescriptionSize, -1f)
            setDescription(description, descriptionColor, descriptionSize)

            // for ui
            val isIconOnly = a.getBoolean(R.styleable.ItemView_isIconOnly, false)
            setIconOnly(isIconOnly)

            val isTextOnly = a.getBoolean(R.styleable.ItemView_isTextOnly, false)
            setTextOnly(isTextOnly)

            val orientation = a.getInt(R.styleable.ItemView_android_orientation, VERTICAL)
            setOrientation(orientation)

            a.recycle()
        }
    }

    override fun setOrientation(orientation: Int) {
        if (orientation == HORIZONTAL) {
            container.orientation = orientation
            with(llText) {
                gravity = GravityCompat.START
                updateLayoutParams<LayoutParams> {
                    setMargins(ResourceUtils.getDimenPixel(R.dimen._5sdp), 0, 0, 0)
                }
            }
        }
    }

    fun setIcon(icon: Drawable?, iconColor: Int = 0, iconSize: Int = -1) {
        with(imageIcon) {
            setImageDrawable(icon)
            setColorFilter(iconColor)

            if (iconSize != -1) {
                updateLayoutParams<LayoutParams> {
                    height = iconSize
                    width = iconSize
                }
            }
        }
    }

    fun setText(text: String?, textColor: Int = 0, textSize: Float = -1f) {
        with(tvText) {
            setText(text)
            if (textColor != 0) {
                setTextColor(textColor)
            }
            if (textSize != -1f) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            }
        }
    }

    fun setDescription(description: String?, textColor: Int = 0, textSize: Float = -1f) {
        with(tvDescription) {
            if (description?.isNotEmpty() == true) {
                gone()
            } else {
                visible()
                text = description
                if (textColor != 0) {
                    setTextColor(textColor)
                }
                if (textSize != -1f) {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                }
            }
        }
    }

    fun setIconOnly(isShow: Boolean) {
        if (isShow) {
            llText.gone()
        } else {
            llText.visible()
        }
    }

    fun setTextOnly(isShow: Boolean) {
        if (isShow) {
            imageIcon.gone()
        } else {
            imageIcon.visible()
        }
    }
}