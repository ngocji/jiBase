@file:JvmName("ViewExtensions")

package com.jibase.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.transition.Transition
import androidx.transition.TransitionManager


fun View.makeTransition(trans: Transition? = null) {
    TransitionManager.beginDelayedTransition(this.rootView as ViewGroup, trans)
}

fun View.gone(isUserAnimation: Boolean = false) {
    if (isUserAnimation) this.makeTransition()
    this.visibility = View.GONE
}

fun View.visible(isUserAnimation: Boolean = false) {
    if (isUserAnimation) this.makeTransition()
    this.visibility = View.VISIBLE
}

fun View.invisible(isUserAnimation: Boolean = false) {
    if (isUserAnimation) this.makeTransition()
    this.visibility = View.INVISIBLE
}

fun View.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

fun View.isState(visible: Int) = this.visibility == visible

fun View.changeElevation(value: Float) {
    ViewCompat.setElevation(this, value)
}

fun View.changeBackground(bg: Drawable?) {
    ViewCompat.setBackground(this, bg)
}

fun View.getCurrentElevation(): Float {
    return ViewCompat.getElevation(this)
}

fun View.inflate(@LayoutRes layoutRes: Int): View {
    return context.inflate(layoutRes)
}

fun Context.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(this).inflate(layoutRes, null)
}


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, attachToRoot)
}


fun View.getDimen(@DimenRes dimen: Int) = context.resources.getDimension(dimen)

fun View.getDimensionPixelOffset(@DimenRes dimen: Int) = context.resources.getDimensionPixelOffset(dimen)