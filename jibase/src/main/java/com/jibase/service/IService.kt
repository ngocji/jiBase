package com.jibase.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder

abstract class IService(private val keepService: Boolean) : Service() {
    companion object {
        const val RQ_RESTART_SERVICE = 119090
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onIntentReceiver(intent)
        return if (keepService) START_STICKY else START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (keepService) {
            val pendingIntent = PendingIntent.getService(this, RQ_RESTART_SERVICE, Intent(this, this::class.java), PendingIntent.FLAG_CANCEL_CURRENT)
            (getSystemService(Context.ALARM_SERVICE) as AlarmManager).set(AlarmManager.RTC, System.currentTimeMillis() + 5000, pendingIntent)
        } else {
            super.onTaskRemoved(rootIntent)
        }
    }

    abstract fun onIntentReceiver(intent: Intent?)
    override fun onBind(intent: Intent?) = IBind()

    inner class IBind : Binder() {
        /**
         * Get service connection
         */
        fun <T : IService> getService(): T? {
            return this@IService as? T
        }
    }
}