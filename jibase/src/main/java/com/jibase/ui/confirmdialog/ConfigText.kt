package com.jibase.ui.confirmdialog

import android.view.Gravity
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes

data class ConfigText(
    var text: String? = null,
    var textColor: Int? = null,
    @StyleRes
    var textStyle: Int = 0,
    @GravityInt
    var textGravity: Int = Gravity.CENTER,
)