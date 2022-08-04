@file:JvmName("ImageExtensions")

package com.jibase.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.load(path: Any?, placeHolder: Int = -1, error: Int = -1) {
    Glide.with(this.context.applicationContext).load(path)
        .apply(RequestOptions().placeholder(placeHolder).error(error)).into(this)
}

fun ImageView.load(
    path: Any?,
    action: ((bitmap: Bitmap) -> Bitmap)?,
    placeHolder: Int = -1,
    error: Int = -1
) {
    Glide.with(this.context.applicationContext).asBitmap().load(path)
        .apply(RequestOptions().placeholder(placeHolder).error(error))
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                this@load.setImageBitmap(action?.invoke(resource) ?: resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                this@load.setImageResource(0)
            }
        })
}

fun ImageView.load(path: Any, listener: RequestListener<Drawable>) {
    val requestOptions = RequestOptions()
    Glide.with(this.context.applicationContext)
        .load(path)
        .apply(requestOptions)
        .listener(listener)
        .into(this)
}

fun ImageView.load(path: Any?, requestOptions: RequestOptions) {
    Glide.with(this.context.applicationContext)
        .load(path)
        .apply(requestOptions)
        .into(this)
}