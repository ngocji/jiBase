package com.jibase.utils

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jibase.R
import com.jibase.extensions.inflate
import com.jibase.extensions.load

object ToastUtils {
    @JvmStatic
    fun showText(context: Context, message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showImage(context: Context, icon: Int, message: String = "", gravity: Int = Gravity.CENTER) {
        val view = context.inflate(R.layout.layout_toast_image)
        view.findViewById<ImageView>(R.id.im_icon).load(icon)
        view.findViewById<TextView>(R.id.tv_mess).text = message
        val toast = Toast(context)
        toast.view = view
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }
}