@file:JvmName("SimpleBinding")

package com.jibinding.helper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jibase.extensions.*


//---- Image Binding ----//
@BindingAdapter("load", "placeHolder", "errorHolder", requireAll = false)
fun loadSimple(v: ImageView, value: Any, placeHolder: Int = -1, errorHolder: Int = -1) {
    v.load(value, placeHolder, errorHolder)
}

//---- Text Binding ----//
@BindingAdapter("makeScroll")
fun makeScroll(v: TextView, boolean: Boolean) {
    if (boolean) {
        v.makeScroll()
    }
}

@BindingAdapter("makeFade")
fun makeFade(v: TextView, heightFade: Int) {
    v.makeFadeTextView(heightFade)
}


//---- View Binding ----//
@BindingAdapter("visibility", "useAnimation", requireAll = false)
fun setVisibilityView(v: View, visible: Int?, isUseAnimation: Boolean? = false) {
    if (visible != null) {
        when (visible) {
            View.VISIBLE -> {
                v.visible(isUseAnimation ?: false)
            }
            View.GONE -> {
                v.gone(isUseAnimation ?: false)
            }
            View.INVISIBLE -> {
                v.invisible(isUseAnimation ?: false)
            }
        }
    }
}

@BindingAdapter("visibility")
fun setVisibility(v: View, visible: Boolean?) {
    if (visible != null) {
        if (visible) v.visible()
        else v.gone()
    }
}


@BindingAdapter("invisibility")
fun setInVisibility(v: View, visible: Boolean?) {
    if (true == visible) v.visible()
    else v.invisible()
}