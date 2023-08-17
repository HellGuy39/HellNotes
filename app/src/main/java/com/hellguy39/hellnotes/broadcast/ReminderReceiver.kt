package com.hellguy39.hellnotes.broadcast

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.activity.main.MainActivity
import com.hellguy39.hellnotes.android_features.AndroidAlarmScheduler
import HNDateHandler
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import com.hellguy39.hellnotes.core.domain.system_features.NotificationSender
import com.hellguy39.hellnotes.core.model.local.datastore.Repeat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject lateinit var reminderRepository: ReminderRepository

    @Inject lateinit var notificationSender: NotificationSender

    @Inject lateinit var alarmScheduler: AlarmScheduler

    private val coroutineScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null && intent == null)
            return

        val message = intent?.extras?.getString(AndroidAlarmScheduler.ALARM_MESSAGE, "")
        val noteId = intent?.extras?.getLong(AndroidAlarmScheduler.ALARM_NOTE_ID, EMPTY_ARG)
        val reminderId = intent?.extras?.getLong(AndroidAlarmScheduler.ALARM_REMINDER_ID, EMPTY_ARG)

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

        if (noteId.isValidId() && reminderId.isValidId()) {
            coroutineScope.launch {
                val reminder = reminderRepository.getReminderById(reminderId!!)

                when(reminder.repeat) {
                    Repeat.DoesNotRepeat -> {
                        reminderRepository.deleteReminder(reminder)
                    }
                    Repeat.Daily -> {
                        val updatedTriggerDate = HNDateHandler
                            .from(reminder.triggerDate)
                            .increaseDays(1)
                            .toMillis()

                        val updatedReminder = reminder.copy(triggerDate = updatedTriggerDate)

                        reminderRepository.updateReminder(updatedReminder)
                        alarmScheduler.scheduleAlarm(updatedReminder)
                    }
                    Repeat.Weekly -> {
                        val updatedTriggerDate = HNDateHandler
                            .from(reminder.triggerDate)
                            .increaseWeeks(1)
                            .toMillis()

                        val updatedReminder = reminder.copy(triggerDate = updatedTriggerDate)

                        reminderRepository.updateReminder(updatedReminder)
                        alarmScheduler.scheduleAlarm(updatedReminder)
                    }
                    Repeat.Monthly -> {
                        val updatedTriggerDate = HNDateHandler
                            .from(reminder.triggerDate)
                            .increaseMonths(1)
                            .toMillis()

                        val updatedReminder = reminder.copy(triggerDate = updatedTriggerDate)

                        reminderRepository.updateReminder(updatedReminder)
                        alarmScheduler.scheduleAlarm(updatedReminder)
                    }
                }
            }
        }
    }

    private fun Long?.isValidId() = this != null && this != EMPTY_ARG

    companion object {

        private const val EMPTY_ARG = -2L

    }
}