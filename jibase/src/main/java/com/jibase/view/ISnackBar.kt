package com.jibase.view

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.jibase.R
import com.google.android.material.snackbar.Snackbar
import com.jibase.utils.ResourceUtils.getColor
import com.jibase.utils.ResourceUtils.getString
import java.lang.IllegalArgumentException

class ISnackBar {
    private val noneColor = getColor(R.color.trans)

    private var rootView: View? = null
    private var message: String = ""
    private var actionName: String = ""
    private var duration: Int = Snackbar.LENGTH_SHORT
    private var action: ((v: View) -> Unit)? = null

    /* styling */
    private var messageColor: Int = noneColor
    private var actionColor: Int = noneColor
    private var backgroundColor: Int = noneColor


    fun of(rootView: View): ISnackBar {
        this.rootView = rootView
        return this
    }

    fun withMessage(message: String): ISnackBar {
        this.message = message
        return this
    }

    fun withMessage(@StringRes messageResId: Int): ISnackBar {
        this.message = getString(messageResId)
        return this
    }

    fun withActionName(actionName: String): ISnackBar {
        this.actionName = actionName
        return this
    }

    fun withActionName(@StringRes actionNameResId: Int): ISnackBar {
        this.actionName = getString(actionNameResId)
        return this
    }

    fun withDuration(duration: Int): ISnackBar {
        this.duration = duration
        return this
    }

    fun setAction(action: ((v: View) -> Unit)): ISnackBar {
        this.action = action
        return this
    }

    fun setTextColor(messageColor: Int): ISnackBar {
        this.messageColor = messageColor
        return this
    }

    fun setActionColor(actionColor: Int): ISnackBar {
        this.actionColor = actionColor
        return this
    }

    fun setBackgroundColor(backgroundColor: Int): ISnackBar {
        this.backgroundColor = backgroundColor
        return this
    }

    @Throws
    fun show(): Snackbar {
        val snack = create()
        snack.show()
        return snack
    }

    @Throws
    fun create(): Snackbar {
        return if (rootView == null) throw IllegalArgumentException("This root view not be null!")
        else
            Snackbar.make(rootView!!, message, duration).apply {
                //Set background
                if (backgroundColor != noneColor) {
                    setBackgroundColor(backgroundColor)
                }
                //Set text color
                if (messageColor != noneColor) {
                    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.also {
                        it.setTextColor(messageColor)
                    }
                }
                // set action color
                if (actionColor != noneColor) {
                    setActionTextColor(actionColor)
                }
                if (actionName.isNotEmpty() && action != null) {
                    setAction(actionName) { v -> action?.invoke(v) }
                }
            }
    }

}