package com.jibase.helper

import android.os.Handler
import android.os.Looper

object UIHelper {
    private val handleUI = Handler(Looper.getMainLooper())

    /**
     *  post task running in UI thread
     *  @param  task: list of task use want
     */
    fun post(vararg task: () -> Unit): Runnable {
        val runnable = Runnable { task.forEach { it() } }
        handleUI.post(runnable)
        return runnable
    }

    /**
     * Delay task in UI thread
     * @param task: task use want delay
     * @param time: time delay
     */
    fun delay(task: () -> Unit, time: Int): Runnable {
        val runnable = Runnable { task() }
        handleUI.postDelayed(runnable, time.toLong())
        return runnable
    }

    /**
     * Remove Runnable post or delay in UI thread
     * @param runnable: runnable
     *
     */
    fun remove(runnable: Runnable?) {
        handleUI.removeCallbacksAndMessages(runnable)
    }

    fun removeAllCallbackAndMessage(){
        handleUI.removeCallbacksAndMessages(null)
    }
}