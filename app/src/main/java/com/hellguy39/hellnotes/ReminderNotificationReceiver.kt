package com.hellguy39.hellnotes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = context?.let { ReminderNotificationService(it) }
        service?.showNotification()
    }
}