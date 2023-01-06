package com.hellguy39.hellnotes.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.domain.repository.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReminderNotificationReceiver : BroadcastReceiver() {

    @Inject lateinit var reminderRepository: ReminderRepository

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let { NotificationHelper(it).showRemindNotification(intent) }

        val noteId = intent?.extras?.getLong(AlarmEventsImpl.ALARM_NOTE_ID)

        if (noteId != null)
            CoroutineScope(Dispatchers.IO).launch {
                reminderRepository.deleteRemindByNoteId(noteId)
            }
    }
}