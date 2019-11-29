package com.jibase.utils

import android.util.DisplayMetrics
import android.util.TypedValue
import com.jibase.BaseApp

private const val EPSILON = 0.00000001f

fun getDisplay(): DisplayMetrics = BaseApp.instance.resources.displayMetrics

fun dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplay())
}

fun spToPx(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplay()).toInt()
}

fun convertPix2Dp(density: Float, px: Int): Int {
    return (px / density + 0.5f).toInt()
}

fun convertDp2Pix(density: Float, dip: Int): Int {
    return (dip * density + 0.5f).toInt()
}

fun convertPix2Sp(fontScale: Float, pxValue: Float): Int {
    return (pxValue / fontScale + 0.5f).toInt()
}

fun convertSp2Pix(fontScale: Float, spValue: Float): Int {
    return (spValue * fontScale + 0.5f).toInt()
}

fun compareFloats(f1: Float, f2: Float): Boolean {
    return Math.abs(f1 - f2) <= EPSILON
}
