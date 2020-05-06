package com.jibase.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.jibase.R
import org.koin.core.KoinComponent
import org.koin.core.inject

object ResourceUtils : KoinComponent {
    private val context: Context by inject()

    fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

    fun getDimen(@DimenRes id: Int): Float = context.resources.getDimension(id)

    fun getDimenPixel(@DimenRes id: Int): Int = context.resources.getDimensionPixelOffset(id)

    fun getInteger(@IntegerRes id: Int): Int = context.resources.getInteger(id)

    fun getFont(@FontRes id: Int): Typeface? {
        return try {
            ResourcesCompat.getFont(context, id)
        } catch (e: Exception) {
            null
        }
    }

    fun getDrawable(@DrawableRes id: Int): Drawable {
        val d = ContextCompat.getDrawable(context, id)
        return d ?: context.resources.getDrawable(R.drawable.ic_error, null)
    }

    fun getBitmap(@DrawableRes id: Int): Bitmap {
        val d = getDrawable(id)
        return (d as BitmapDrawable).bitmap
    }

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getAnimation(@AnimRes id: Int): Animation =
            AnimationUtils.loadAnimation(context, id)

    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    fun getTypeArray(id: Int): TypedArray {
        return context.resources.obtainTypedArray(id)
    }

    fun getResourceId(name: String, def: String): Int {
        return context.resources.getIdentifier(name, def, context.packageName)
    }

    fun String.toColor(): Int = Color.parseColor(this)
    fun drawableToString(id: Int) = "android.resource://" + context.packageName + "/" + id
    fun drawableToString(name: String) =
            "android.resource//" + context.packageName + "/drawable/" + name

}