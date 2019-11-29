package com.jibase.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.load(path: Any, placeHolder: Int = -1, error: Int = -1) {
    Glide.with(this.context).load(path).apply(RequestOptions().placeholder(placeHolder).error(error)).into(this)
}

fun ImageView.load(path: Any, action: ((bitmap: Bitmap) -> Bitmap)?, placeHolder: Int = -1, error: Int = -1) {
    Glide.with(this.context).asBitmap().load(path).apply(RequestOptions().placeholder(placeHolder).error(error)).into(object : SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            this@load.setImageBitmap(action?.invoke(resource) ?: resource)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            this@load.setImageResource(0)
        }
    })
}

fun ImageView.load(path: Any) {
    load(path, null)
}