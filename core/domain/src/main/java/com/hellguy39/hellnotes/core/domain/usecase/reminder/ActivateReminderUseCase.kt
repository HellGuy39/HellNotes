package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.common.date.DateManager
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.tools.AlarmScheduler
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import javax.inject.Inject

class ActivateReminderUseCase
    @Inject
    constructor(
        private val reminderRepository: ReminderRepository,
        private val alarmScheduler: AlarmScheduler,
    ) {
        suspend operator fun invoke(reminderId: Long) {
            // TODO: handle null
            val reminder = reminderRepository.getReminderById(reminderId) ?: return

            if (reminder.repeat is Repeat.DoesNotRepeat) {
                reminderRepository.deleteReminder(reminder)
            } else {
                val nextTriggerDate =
                    DateManager.from(reminder.triggerDate)
                        .increase(reminder.repeat)
                        .toMillis()
                val updatedReminder =
                    reminder.copy(triggerDate = nextTriggerDate)
                reminderRepository.updateReminder(updatedReminder)
                alarmScheduler.scheduleAlarm(updatedReminder)
            }
        }
    }
