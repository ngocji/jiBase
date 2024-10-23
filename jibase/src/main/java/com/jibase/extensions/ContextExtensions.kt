@file:JvmName("ContextExtensions")

package com.jibase.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jibase.utils.Utils

fun Context.getColorByAttr(attrId: Int): Int {
    return Utils.getColorByAttr(this, attrId)
}

fun Context.showToast(@StringRes text: Int) {
    showToast(getString(text))
}

fun Context.showLongToast(@StringRes text: Int) {
    showLongToast(getString(text))
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.getColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getDimen(@DimenRes dimen: Int) = resources.getDimension(dimen)

fun Context.getDimensionPixelOffset(@DimenRes dimen: Int) = resources.getDimensionPixelOffset(dimen)
