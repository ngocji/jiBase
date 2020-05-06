package com.jibase.extensions

import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView

fun TextView.makeScroll() {
    ellipsize = TextUtils.TruncateAt.MARQUEE
    isSelected = true
    marqueeRepeatLimit = Int.MAX_VALUE
    isSingleLine = true
}

@Suppress("DEPRECATION")
fun TextView.setTextHtml(string: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(string)
    }
}

fun View.makeFadeTextView(heightFade: Int) {
    setFadingEdgeLength(heightFade)
}