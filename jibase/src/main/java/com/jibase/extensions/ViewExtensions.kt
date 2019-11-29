package com.jibase.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
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