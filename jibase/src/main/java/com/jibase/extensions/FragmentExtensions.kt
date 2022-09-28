package com.jibase.extensions

import androidx.fragment.app.Fragment

fun <T : Fragment> T.getTagName() = this::class.java.name