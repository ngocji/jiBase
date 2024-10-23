@file:JvmName("ContextExtensions")

package com.jibase.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jibase.utils.Utils

@SuppressLint("MissingPermission")
fun Context.isOnline(): Boolean = try {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val activeNetworkInfo = connectivityManager?.activeNetworkInfo
    activeNetworkInfo != null && activeNetworkInfo.isConnected
} catch (ex: Exception) {
    ex.printStackTrace()
    false
}

fun Context.checkAppInstalled(uri: String?): Boolean {
    val pm = packageManager
    try {
        pm.getPackageInfo(uri ?: return false, PackageManager.GET_ACTIVITIES)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return false
}


fun Context.spToPx(sp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        resources.displayMetrics
    ).toInt()
}

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.pxToDp(px: Int): Int {
    return (px / resources.displayMetrics.density).toInt()
}

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
