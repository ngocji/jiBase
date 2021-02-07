package com.jibase.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
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
            setItemIcon(a.getDrawable(R.styleable.ItemView_itemIcon))
            setItemIconColor(a.getColor(R.styleable.ItemView_itemIconColor, 0))
            setItemIconSize(a.getDimension(R.styleable.ItemView_itemIconSize, 0f))

            // for text
            setItemText(a.getString(R.styleable.ItemView_itemText))
            setItemTextColor(a.getColor(R.styleable.ItemView_itemTextColor, 0))
            setItemTextSize(a.getDimension(R.styleable.ItemView_itemTextSize, -1f))
            setItemTextGravity(a.getInt(R.styleable.ItemView_itemTextGravity, Gravity.CENTER))
            setItemTextLines(a.getInt(R.styleable.ItemView_itemTextLines, -1))
            setItemTextMaxLines(a.getInt(R.styleable.ItemView_itemTextMaxLines, -1))

            // for description
            setItemDescription(a.getString(R.styleable.ItemView_itemDescription))
            setItemDescriptionColor(a.getColor(R.styleable.ItemView_itemDescriptionColor, 0))
            setItemDescriptionSize(a.getDimension(R.styleable.ItemView_itemDescriptionSize, -1f))
            setItemDescriptionGravity(
                a.getInt(
                    R.styleable.ItemView_itemTextGravity,
                    Gravity.CENTER
                )
            )
            setItemDescriptionLines(a.getInt(R.styleable.ItemView_itemTextLines, -1))
            setItemDescriptionMaxLines(a.getInt(R.styleable.ItemView_itemTextMaxLines, -1))

            // for global
            setIconOnly(a.getBoolean(R.styleable.ItemView_isIconOnly, false))
            setTextOnly(a.getBoolean(R.styleable.ItemView_isTextOnly, false))
            setOrientation(a.getInt(R.styleable.ItemView_android_orientation, VERTICAL))

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
        if (textSize != -1f) {
            tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }

    fun setItemTextSize(@DimenRes textSizeRes: Int) {
        if (textSizeRes != NO_ID) {
            setItemTextSize(context.resources.getDimension(textSizeRes))
        }
    }

    fun setItemTextGravity(gravity: Int) {
        tvText.gravity = gravity
    }

    fun setItemTextLines(lines: Int) {
        if (lines <= 0) return
        tvText.setLines(lines)
    }

    fun setItemTextMaxLines(lines: Int) {
        if (lines <= 0) return
        tvText.maxLines = lines
    }

    fun setItemDescription(@StringRes text: Int) {
        tvText.setText(text)
    }

    fun setItemDescription(text: String?) {
        tvDescription.text = text
    }

    fun setItemDescriptionColor(@ColorInt color: Int) {
        if (color != 0) {
            tvDescription.setTextColor(color)
        }
    }

    fun setItemDescriptionSize(textSize: Float) {
        if (textSize != -1f) {
            tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }

    fun setItemDescriptionSize(@DimenRes textSizeRes: Int) {
        if (textSizeRes != NO_ID) {
            setItemDescriptionSize(context.resources.getDimension(textSizeRes))
        }
    }

    fun setItemDescriptionGravity(gravity: Int) {
        tvDescription.gravity = gravity
    }

    fun setItemDescriptionLines(lines: Int) {
        if (lines <= 0) return
        tvDescription.setLines(lines)
    }

    fun setItemDescriptionMaxLines(lines: Int) {
        if (lines <= 0) return
        tvDescription.maxLines = lines
    }

    fun setIconOnly(isShow: Boolean) {
        if (isShow) {
            llText.gone()
        }
    }

    fun setTextOnly(isShow: Boolean) {
        if (isShow) {
            imageIcon.gone()
        }
    }
}