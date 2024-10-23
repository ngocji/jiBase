package com.jibase.extensions

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity


inline fun <reified T> FragmentActivity.navigate(bundle: Bundle? = null) {
    startActivity(Intent(this, T::class.java).apply {
        bundle?.also { putExtras(bundle) }
    })
}

inline fun <reified T> FragmentActivity.navigateResult(
    launcher: ActivityResultLauncher<Intent>,
    bundle: Bundle? = null
) {
    launcher.launch(Intent(this, T::class.java).apply {
        bundle?.also { putExtras(bundle) }
    })
}