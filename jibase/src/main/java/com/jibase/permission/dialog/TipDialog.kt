package com.jibase.permission.dialog

import android.os.Build
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import com.jibase.R
import com.jibase.ui.dialog.NormalDialog

@RequiresApi(Build.VERSION_CODES.M)
class TipDialog(@LayoutRes private val layoutResId: Int,
                private val message: Pair<String, String>,
                private val callback: CallBack) : NormalDialog(layoutResId) {
    override fun onViewReady() {
        view?.findViewById<TextView>(R.id.tv_title)?.text = message.first
        view?.findViewById<TextView>(R.id.tv_mess)?.text = message.second
    }

    override fun onViewListener() {
        dialog?.setOnCancelListener {
            callback.onCancelClicked()
        }
        view?.findViewById<FrameLayout>(R.id.fr_request)?.setOnClickListener {
            dismiss()
            callback.onOkClicked()
        }
        view?.findViewById<FrameLayout>(R.id.fr_cancel)?.setOnClickListener {
            dismiss()
            callback.onCancelClicked()
        }
    }

    interface CallBack {
        fun onOkClicked()
        fun onCancelClicked()
    }
}