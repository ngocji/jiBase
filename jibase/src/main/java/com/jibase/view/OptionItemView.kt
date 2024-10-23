package com.jibase.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import com.jibase.R
import com.jibase.databinding.LayoutOptionItemBinding
import com.jibase.extensions.getDimensionPixelOffset
import com.jibase.extensions.gone
import com.jibase.extensions.setLayoutParams
import com.jibase.extensions.visible

@Suppress("MemberVisibilityCanBePrivate", "unused", "JoinDeclarationAndAssignment")
class OptionItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : ConstraintLayout(context, attrs, def) {
    private val binding: LayoutOptionItemBinding

    enum class Type(val value: Int) {
        NONE(-1),
        SWITCH(0),
        TEXT(1),
        IMAGE(2);

        companion object {
            fun safe(v: Int?): Type {
                return entries.find { it.value == v } ?: NONE
            }
        }
    }

    init {
        binding = LayoutOptionItemBinding.inflate(
            LayoutInflater.from(context),
            this
        )

        minHeight = context.getDimensionPixelOffset(R.dimen.min_height_option)


        // load attr
        attrs?.also { attr ->
            val typeArray =
                context.obtainStyledAttributes(attr, R.styleable.OptionItemView, 0, 0)

            setTitle(typeArray.getString(R.styleable.OptionItemView_ot_title))

            val stateTitleColor =
                typeArray.getColorStateList(R.styleable.OptionItemView_ot_title_color)
            if (stateTitleColor != null) {
                setTitleColor(stateTitleColor)
            } else {
                setTitleColor(typeArray.getColor(R.styleable.OptionItemView_ot_title_color, 0))
            }

            setDesc(typeArray.getString(R.styleable.OptionItemView_ot_desc))

            val stateDescColor =
                typeArray.getColorStateList(R.styleable.OptionItemView_ot_desc_color)
            if (stateDescColor != null) {
                setDescColor(stateDescColor)
            } else {
                setDescColor(typeArray.getColor(R.styleable.OptionItemView_ot_desc_color, 0))
            }

            setIcon(
                typeArray.getResourceId(
                    R.styleable.OptionItemView_ot_icon,
                    0
                )
            )
            setIconSize(
                typeArray.getDimensionPixelOffset(
                    R.styleable.OptionItemView_ot_icon_size,
                    -1
                )
            )
            val stateIconColor =
                typeArray.getColorStateList(R.styleable.OptionItemView_ot_icon_tint)
            if (stateIconColor != null) {
                setIconTint(stateIconColor)
            } else {
                setIconTint(typeArray.getColor(R.styleable.OptionItemView_ot_icon_tint, 0))
            }


            setEndIcon(
                typeArray.getResourceId(
                    R.styleable.OptionItemView_ot_end_icon,
                    0
                )
            )
            setEndIconSize(
                typeArray.getDimensionPixelOffset(
                    R.styleable.OptionItemView_ot_end_icon_size,
                    -1
                )
            )

            val stateEndIconColor =
                typeArray.getColorStateList(R.styleable.OptionItemView_ot_end_icon_tint)
            if (stateEndIconColor != null) {
                setEndIconTint(stateEndIconColor)
            } else {
                setEndIconTint(typeArray.getColor(R.styleable.OptionItemView_ot_end_icon_tint, 0))
            }

            setSwitchButton(
                typeArray.getResourceId(
                    R.styleable.OptionItemView_ot_switch_button,
                    -1
                )
            )

            setType(Type.safe(typeArray.getInt(R.styleable.OptionItemView_ot_type, -1)))

            binding.line.isVisible =
                typeArray.getBoolean(R.styleable.OptionItemView_ot_show_line, true)

            typeArray.recycle()
        }
    }

    fun setImageData(res: Int) {
        binding.imageData.setImageResource(res)
    }

    fun setTextData(text: String?) {
        binding.tvText.text = text
    }

    fun setSwitchEnable(enable: Boolean) {
        binding.sw.isEnabled = enable
    }

    fun setChecked(checked: Boolean) {
        binding.sw.isChecked = checked
    }

    fun isChecked() = binding.sw.isChecked

    fun setOnCheckedChanged(action: (CompoundButton, Boolean) -> Unit) {
        binding.sw.setOnCheckedChangeListener { buttonView, isChecked ->
            action.invoke(buttonView, isChecked)
        }
    }

    fun setTitle(title: String?) {
        binding.tvTitle.text = title.orEmpty()
    }

    fun setTitleColor(color: ColorStateList?) {
        if (color == null) return
        binding.tvTitle.setTextColor(color)
    }

    fun setTitleColor(color: Int) {
        if (color == 0) return
        binding.tvTitle.setTextColor(color)
    }

    fun setDesc(text: String?) {
        if (!text.isNullOrBlank()) {
            binding.tvDesc.visible()
            binding.tvDesc.text = text
        }
    }


    fun setDescColor(color: ColorStateList?) {
        if (color == null) return
        binding.tvDesc.setTextColor(color)
    }

    fun setDescColor(color: Int) {
        if (color == 0) return
        binding.tvDesc.setTextColor(color)
    }

    fun setIcon(iconRes: Int) {
        with(binding.imageIcon) {
            if (iconRes <= 0) {
                gone()
            } else {
                visible()
                setImageResource(iconRes)
            }
        }
    }

    fun setIconSize(size: Int) {
        if (size <= 0) return
        binding.imageIcon.setLayoutParams(width = size, height = size)
    }

    fun setIconTint(color: Int) {
        with(binding.imageIcon) {
            if (color == 0) {
                clearColorFilter()
            } else {
                setColorFilter(color)
            }
        }
    }

    fun setIconTint(color: ColorStateList?) {
        with(binding.imageIcon) {
            if (color == null) {
                clearColorFilter()
            } else {
                ImageViewCompat.setImageTintList(this, color)
            }
        }
    }

    fun setEndIcon(iconRes: Int) {
        with(binding.imageEndIcon) {
            if (iconRes <= 0) {
                gone()
            } else {
                visible()
                setImageResource(iconRes)
            }
        }
    }

    fun setEndIconSize(size: Int) {
        if (size <= 0) return
        binding.imageEndIcon.setLayoutParams(width = size, height = size)
    }

    fun setEndIconTint(color: Int) {
        with(binding.imageEndIcon) {
            if (color == 0) {
                clearColorFilter()
            } else {
                setColorFilter(color)
            }
        }
    }


    fun setEndIconTint(color: ColorStateList?) {
        with(binding.imageEndIcon) {
            if (color == null) {
                clearColorFilter()
            } else {
                ImageViewCompat.setImageTintList(this, color)
            }
        }
    }

    fun setType(type: Type) {
        when (type) {
            Type.SWITCH -> {
                binding.flEnd.visible()
                binding.sw.visible()
            }

            Type.TEXT -> {
                binding.flEnd.visible()
                binding.tvText.visible()
            }

            Type.IMAGE -> {
                binding.flEnd.visible()
                binding.imageData.visible()
            }

            else -> {
                binding.flEnd.gone()
            }
        }
    }

    fun setSwitchButton(res: Int) {
        if (res <= 0) return
        with(binding.sw) {
            setButtonDrawable(res)
            thumbDrawable = null
            background = null
        }
    }
}