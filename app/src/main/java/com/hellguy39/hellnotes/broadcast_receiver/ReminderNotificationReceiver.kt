package com.hellguy39.hellnotes.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.service.ReminderNotificationService

class ReminderNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = context?.let { ReminderNotificationService(it) }
        service?.showNotification()
    }
}