package com.jibase.utils

import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.jibase.BaseApp
import com.jibase.R

fun getColorResource(id: Int): Int = ContextCompat.getColor(BaseApp.instance, id)

fun getDimenResource(id: Int): Float =
    if (id <= 0) 0f else BaseApp.instance.resources.getDimension(id)

fun getDimenPixelOffsetResource(id: Int): Int =
    if (id <= 0) 0 else BaseApp.instance.resources.getDimensionPixelOffset(id)

fun getIntegerResource(id: Int): Int = BaseApp.instance.resources.getInteger(id)

fun getFontResource(id: Int): Typeface? {
    return try {
        ResourcesCompat.getFont(BaseApp.instance, id)
    } catch (e: Exception) {
        null
    }
}

fun hasResource(id: Int) = id > 0

fun getDrawableResource(id: Int): Drawable {
    val d = ContextCompat.getDrawable(BaseApp.instance, id)
    return d ?: BaseApp.instance.resources.getDrawable(R.drawable.ic_error)
}

fun getBitmapResource(id: Int): Bitmap {
    val d = getDrawableResource(id)
    return (d as BitmapDrawable).bitmap
}

fun getStringResource(id: Int): String {
    return BaseApp.instance.getString(id)
}

fun getAnimationResource(id: Int): Animation = AnimationUtils.loadAnimation(BaseApp.instance, id)

fun getStringArrayResource(id: Int): Array<String> {
    return BaseApp.instance.resources.getStringArray(id)
}

fun getTypeArrayResource(id: Int): TypedArray {
    return BaseApp.instance.resources.obtainTypedArray(id)
}

fun getResourceId(name: String, def: String): Int {
    return BaseApp.instance.resources.getIdentifier(name, def, BaseApp.instance.packageName)
}

fun String.toColor(): Int = Color.parseColor(this)
fun drawableToString(id: Int) = "android.resource://" + BaseApp.instance.packageName + "/" + id
fun drawableToString(name: String) =
    "android.resource//" + BaseApp.instance.packageName + "/drawable/" + name
