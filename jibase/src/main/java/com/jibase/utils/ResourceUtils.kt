package com.jibase.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.jibase.R
import org.koin.core.KoinComponent
import org.koin.core.inject

object ResourceUtils : KoinComponent {
    private val context: Context by inject()

    @JvmStatic
    fun getColor(@ColorRes id: Int) = ContextCompat.getColor(context, id)

    @JvmStatic
    fun getDimen(@DimenRes id: Int) = context.resources.getDimension(id)

    @JvmStatic
    fun getDimenPixel(@DimenRes id: Int) = context.resources.getDimensionPixelOffset(id)

    @JvmStatic
    fun getInteger(@IntegerRes id: Int) = context.resources.getInteger(id)

    @JvmStatic
    fun getFont(@FontRes id: Int): Typeface? {
        return try {
            ResourcesCompat.getFont(context, id)
        } catch (e: Exception) {
            null
        }
    }

    @JvmStatic
    fun getDrawable(@DrawableRes id: Int): Drawable {
        val d = ContextCompat.getDrawable(context, id)
        return d ?: context.resources.getDrawable(R.drawable.ic_error, null)
    }

    @JvmStatic
    fun getBitmap(@DrawableRes id: Int): Bitmap {
        val d = getDrawable(id)
        return (d as BitmapDrawable).bitmap
    }

    @JvmStatic
    fun getString(@StringRes id: Int) = context.getString(id)


    @JvmStatic
    fun getAnimation(@AnimRes id: Int) = AnimationUtils.loadAnimation(context, id)

    @JvmStatic
    fun getStringArray(@ArrayRes id: Int) = context.resources.getStringArray(id)


    @JvmStatic
    fun getTypeArray(id: Int) = context.resources.obtainTypedArray(id)


    @JvmStatic
    fun getResourceId(name: String, def: String) = context.resources.getIdentifier(name, def, context.packageName)

    @JvmStatic
    fun String.toColor() = Color.parseColor(this)

    @JvmStatic
    fun drawableToString(id: Int) = "android.resource://" + context.packageName + "/" + id

    @JvmStatic
    fun drawableToString(name: String) = "android.resource//" + context.packageName + "/drawable/" + name

}