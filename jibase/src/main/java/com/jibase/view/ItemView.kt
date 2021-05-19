package com.jibase.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.ImageViewCompat
import com.jibase.R
import com.jibase.extensions.gone
import com.jibase.extensions.inflate
import com.jibase.extensions.load
import com.jibase.extensions.visible
import com.jibase.utils.ResourceUtils
import kotlinx.android.synthetic.main.layout_item_view.view.*
import kotlin.math.roundToInt

open class ItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {
    private var iconTint: ColorStateList? = null

    init {
        initView(attributeSet)
    }

    open fun initView(attrs: AttributeSet?) {
        inflate(R.layout.layout_item_view, true)

        attrs?.also {
            val a = context.resources.obtainAttributes(it, R.styleable.ItemView)

            // for icon
            setItemIcon(a.getDrawable(R.styleable.ItemView_itemIcon))

            iconTint = a.getColorStateList(R.styleable.ItemView_itemIconColor)
            if (iconTint != null) {
                setItemIconColor(iconTint)
            } else {
                setItemIconColor(a.getColor(R.styleable.ItemView_itemIconColor, 0))
            }
            setItemIconSize(
                a.getDimension(R.styleable.ItemView_itemIconSize, 0f),
                a.getDimensionPixelOffset(R.styleable.ItemView_itemIconPadding, 0)
            )
            setItemIconRotation(a.getInt(R.styleable.ItemView_itemIconRotation, 0))
            setItemIconBackground(a.getResourceId(R.styleable.ItemView_itemIconBackground, 0))
            setItemIconMargin(
                a.getDimensionPixelOffset(R.styleable.ItemView_itemIconMarginStart, 0),
                a.getDimensionPixelOffset(R.styleable.ItemView_itemIconMarginTop, 0),
                a.getDimensionPixelOffset(R.styleable.ItemView_itemIconMarginEnd, 0),
                a.getDimensionPixelOffset(R.styleable.ItemView_itemIconMarginBottom, 0),
                a.getDimensionPixelOffset(R.styleable.ItemView_itemIconMargin, 0)
            )

            // for text
            setItemText(a.getString(R.styleable.ItemView_itemText))

            val textColor = a.getColorStateList(R.styleable.ItemView_itemTextColor)
            if (textColor != null) {
                setItemTextColor(textColor)
            } else {
                setItemTextColor(a.getColor(R.styleable.ItemView_itemTextColor, 0))
            }

            setItemTextSize(a.getDimension(R.styleable.ItemView_itemTextSize, -1f))
            setItemTextGravity(a.getInt(R.styleable.ItemView_itemTextGravity, Gravity.CENTER))
            setItemTextLines(a.getInt(R.styleable.ItemView_itemTextLines, -1))
            setItemTextMaxLines(a.getInt(R.styleable.ItemView_itemTextMaxLines, -1))
            setItemTextFont(
                a.getInt(R.styleable.ItemView_itemTextFont, 0),
                a.getInt(R.styleable.ItemView_itemTextStyle, 0)
            )

            // for description
            setItemDescription(a.getString(R.styleable.ItemView_itemDescription))

            val descColor = a.getColorStateList(R.styleable.ItemView_itemDescriptionColor)
            if (descColor != null) {
                setItemDescriptionColor(descColor)
            } else {
                setItemDescriptionColor(a.getColor(R.styleable.ItemView_itemDescriptionColor, 0))
            }
            setItemDescriptionSize(a.getDimension(R.styleable.ItemView_itemDescriptionSize, -1f))
            setItemDescriptionGravity(
                a.getInt(
                    R.styleable.ItemView_itemDescriptionGravity,
                    Gravity.CENTER
                )
            )
            setItemDescriptionLines(a.getInt(R.styleable.ItemView_itemTextLines, -1))
            setItemDescriptionMaxLines(a.getInt(R.styleable.ItemView_itemTextMaxLines, -1))
            setItemDescriptionFont(
                a.getInt(R.styleable.ItemView_itemDescriptionFont, 0),
                a.getInt(R.styleable.ItemView_itemDescriptionStyle, 0)
            )

            // for global
            setIconOnly(a.getBoolean(R.styleable.ItemView_isIconOnly, false))
            setTextOnly(a.getBoolean(R.styleable.ItemView_isTextOnly, false))
            setOrientation(a.getInt(R.styleable.ItemView_android_orientation, VERTICAL))

            a.recycle()
        }
    }

    override fun setOrientation(orientation: Int) {
        if (orientation == HORIZONTAL && container != null) {
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
        setItemIconColor(iconTint)
    }

    fun setItemIcon(icon: Drawable?) {
        if (icon != null) imageIcon.setImageDrawable(icon)
        setItemIconColor(iconTint)
    }

    fun setItemIcon(icon: String) {
        imageIcon.load(icon)
    }

    fun setItemIconColor(color: Int) {
        if (color == 0) {
            imageIcon.clearColorFilter()
        } else {
            ImageViewCompat.setImageTintList(imageIcon, ColorStateList.valueOf(color))
        }
    }

    fun setItemIconColor(colorStateList: ColorStateList?) {
        iconTint = colorStateList
        ImageViewCompat.setImageTintList(imageIcon, iconTint)
    }

    fun setItemIconSize(size: Float, padd: Int) {
        if (size > 0f) {
            imageIcon.updateLayoutParams<LayoutParams> {
                height = size.roundToInt()
                width = size.roundToInt()
            }

            imageIcon.updatePadding(padd, padd, padd, padd)
        }
    }

    fun setItemIconRotation(rotation: Int) {
        imageIcon.rotation = rotation.toFloat()
    }

    fun setItemIconSize(@DimenRes sizeRes: Int) {
        if (sizeRes != NO_ID) {
            setItemIconSize(context.resources.getDimension(sizeRes), 0)
        }
    }

    fun setItemIconBackground(@DrawableRes resourceId: Int) {
        if (resourceId == NO_ID) return
        imageIcon.setBackgroundResource(resourceId)
    }

    fun setItemIconMargin(start: Int, top: Int, end: Int, bottom: Int, margin: Int) {
        if (start != 0 || top != 0 || end != 0 || bottom != 0 || margin != 0) {
            imageIcon.updateLayoutParams<LayoutParams> {
                if (margin != 0) {
                    setMargins(margin, margin, margin, margin)
                } else {
                    setMargins(start, top, end, bottom)
                }
            }
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

    fun setItemTextColor(colorStateList: ColorStateList?) {
        tvText.setTextColor(colorStateList)
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

    fun setItemTextFont(fontId: Int, style: Int) {
        tvText.typeface =
            Typeface.create(
                if (fontId != 0) ResourceUtils.getFont(fontId) else tvText.typeface,
                style
            )
    }

    fun setItemDescription(@StringRes text: Int) {
        tvText.setText(text)
    }

    fun setItemDescription(text: String?) {
        if (text?.isNotBlank() == true) {
            tvDescription.text = text
            tvDescription.visible()
        }
    }

    fun setItemDescriptionColor(@ColorInt color: Int) {
        if (color != 0) {
            tvDescription.setTextColor(color)
        }
    }

    fun setItemDescriptionColor(colorStateList: ColorStateList?) {
        tvDescription.setTextColor(colorStateList)
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

    fun setItemDescriptionFont(fontId: Int, style: Int) {
        tvDescription.typeface =
            Typeface.create(
                if (fontId != 0) ResourceUtils.getFont(fontId) else tvDescription.typeface,
                style
            )
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