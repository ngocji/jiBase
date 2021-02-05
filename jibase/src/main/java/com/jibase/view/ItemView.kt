package com.jibase.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import com.jibase.R
import com.jibase.extensions.gone
import com.jibase.extensions.inflate
import com.jibase.extensions.visible
import com.jibase.utils.ResourceUtils
import kotlinx.android.synthetic.main.layout_item_view.view.*
import kotlin.math.roundToInt

open class ItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {
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
            val iconSize = a.getDimension(R.styleable.ItemView_itemIconSize, 0f)
            setItemIcon(icon)
            setItemIconColor(iconColor)
            setItemIconSize(iconSize)

            // for text
            val text = a.getString(R.styleable.ItemView_itemText)
            val textColor = a.getColor(R.styleable.ItemView_itemTextColor, 0)
            val textSize = a.getDimension(R.styleable.ItemView_isTextOnly, 0f)
            setItemText(text)
            setItemTextColor(textColor)
            setItemTextSize(textSize)

            // for description
            val description = a.getString(R.styleable.ItemView_itemDescription)
            val descriptionColor = a.getColor(R.styleable.ItemView_itemDescriptionColor, 0)
            val descriptionSize = a.getDimension(R.styleable.ItemView_itemDescriptionSize, 0f)

            setItemDescription(description)
            setItemDescriptionColor(descriptionColor)
            setItemDescriptionSize(descriptionSize)

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

    fun setItemIcon(@DrawableRes icon: Int) {
        imageIcon.setImageResource(icon)
    }

    fun setItemIcon(icon: Drawable?) {
        if (icon != null) imageIcon.setImageDrawable(icon)
    }

    fun setItemIconColor(color: Int) {
        if (color == 0) {
            imageIcon.clearColorFilter()
        } else {
            imageIcon.setColorFilter(color)
        }
    }

    fun setItemIconSize(size: Float) {
        if (size > 0f) {
            imageIcon.updateLayoutParams<LayoutParams> {
                height = size.roundToInt()
                width = size.roundToInt()
            }
        }
    }

    fun setItemIconSize(@DimenRes sizeRes: Int) {
        if (sizeRes != NO_ID) {
            setItemIconSize(context.resources.getDimension(sizeRes))
        }
    }

    fun setItemText(@StringRes text: Int) {
        tvText.setText(text)
    }

    fun setItemText(text: String?) {
        tvText.text = text
    }

    fun setItemTextColor(@ColorInt color: Int) {
        if (color != 0) {
            tvText.setTextColor(color)
        }
    }

    fun setItemTextSize(textSize: Float) {
        if (textSize != 0f) {
            tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }

    fun setItemTextSize(@DimenRes textSizeRes: Int) {
        if (textSizeRes != NO_ID) {
            setItemTextSize(context.resources.getDimension(textSizeRes))
        }
    }

    fun setItemDescription(@StringRes text: Int) {
        tvText.setText(text)
    }

    fun setItemDescription(text: String?) {
        tvText.text = text
    }

    fun setItemDescriptionColor(@ColorInt color: Int) {
        if (color != 0) {
            tvText.setTextColor(color)
        }
    }

    fun setItemDescriptionSize(textSize: Float) {
        if (textSize != 0f) {
            tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }

    fun setItemDescriptionSize(@DimenRes textSizeRes: Int) {
        if (textSizeRes != NO_ID) {
            setItemTextSize(context.resources.getDimension(textSizeRes))
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