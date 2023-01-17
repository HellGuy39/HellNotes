package com.hellguy39.hellnotes.broadcast

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.activity.main.MainActivity
import com.hellguy39.hellnotes.android_features.AndroidAlarmScheduler
import com.hellguy39.hellnotes.core.domain.system_features.NotificationSender
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReminderBroadcastReceiver : BroadcastReceiver() {

    @Inject lateinit var reminderRepository: ReminderRepository

    @Inject lateinit var notificationSender: NotificationSender

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null && intent == null)
            return

        val message = intent?.extras?.getString(AndroidAlarmScheduler.ALARM_MESSAGE)
        val noteId = intent?.extras?.getLong(AndroidAlarmScheduler.ALARM_NOTE_ID)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(AndroidAlarmScheduler.ALARM_NOTE_ID, noteId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationIntent.hashCode(),
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationSender.send(
            message = message.toString(),
            pendingIntent = pendingIntent
        )

        if (noteId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                reminderRepository.deleteRemindByNoteId(noteId)
            }
        }
    }
}