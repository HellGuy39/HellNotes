package com.hellguy39.hellnotes.component.broadcast

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.component.main.MainActivity
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.tools.AlarmScheduler
import com.hellguy39.hellnotes.core.domain.tools.NotificationSender
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.tools.AlarmSchedulerImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject lateinit var reminderRepository: ReminderRepository

    @Inject lateinit var notificationSender: NotificationSender

    @Inject lateinit var alarmScheduler: AlarmScheduler

    private val coroutineScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (context == null || intent == null) return

        val message = intent.extras?.getString(AlarmSchedulerImpl.ALARM_MESSAGE, "")
        val noteId = intent.extras?.getLong(AlarmSchedulerImpl.ALARM_NOTE_ID, EMPTY_ARG)
        val reminderId = intent.extras?.getLong(AlarmSchedulerImpl.ALARM_REMINDER_ID, EMPTY_ARG)

        val notificationIntent =
            Intent(context, MainActivity::class.java).apply {
                putExtra(AlarmSchedulerImpl.ALARM_NOTE_ID, noteId)
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                notificationIntent.hashCode(),
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )

        notificationSender.send(
            message = message.toString(),
            pendingIntent = pendingIntent,
        )

        if (noteId.isValidId() && reminderId.isValidId()) {
            coroutineScope.launch {
                val reminder = reminderRepository.getReminderById(reminderId!!)

                if (reminder.repeat is Repeat.DoesNotRepeat) {
                    reminderRepository.deleteReminder(reminder)
                } else {
                    val updatedReminder =
                        reminder.copy(
                            triggerDate =
                                calculateNextTriggerDate(
                                    reminder.repeat,
                                    reminder.triggerDate,
                                ),
                        )
                    reminderRepository.updateReminder(updatedReminder)
                    alarmScheduler.scheduleAlarm(updatedReminder)
                }
            }
        }
    }

    private fun calculateNextTriggerDate(
        repeat: Repeat,
        triggerDate: Long,
    ): Long {
        return when (repeat) {
            Repeat.Daily -> DateTimeUtils.increaseDays(triggerDate, 1)
            Repeat.Weekly -> DateTimeUtils.increaseWeeks(triggerDate, 1)
            Repeat.Monthly -> DateTimeUtils.increaseMonths(triggerDate, 1)
            else -> triggerDate
        }
    }

    private fun Long?.isValidId() = this != null && this != EMPTY_ARG

    companion object {
        private const val EMPTY_ARG = -2L
    }
}
