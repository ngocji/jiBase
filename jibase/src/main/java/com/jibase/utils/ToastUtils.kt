package com.jibase.utils

import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jibase.BaseApp
import com.jibase.R
import com.jibase.extensions.inflate
import com.jibase.extensions.load


fun showToast(mess: String) {
    Toast.makeText(BaseApp.instance, mess, Toast.LENGTH_SHORT).show()
}

fun showToastImage(icon: Int, mess: String, gravity: Int = Gravity.CENTER) {
    val view = BaseApp.instance.inflate(R.layout.layout_toast_image)
    view.findViewById<ImageView>(R.id.im_icon).load(icon)
    view.findViewById<TextView>(R.id.tv_mess).text = mess
    val toast = Toast(BaseApp.instance)
    toast.view = view
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(gravity, 0, 0)
    toast.show()
}