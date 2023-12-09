package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.tools.AlarmScheduler
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import javax.inject.Inject

class UpdateReminderUseCase
    @Inject
    constructor(
        private val reminderRepository: ReminderRepository,
        private val alarmScheduler: AlarmScheduler,
    ) {
        suspend operator fun invoke(reminder: Reminder) {
            val oldReminder = reminderRepository.getReminderById(reminder.id ?: return)
            reminderRepository.updateReminder(reminder)

            alarmScheduler.cancelAlarm(oldReminder)
            alarmScheduler.scheduleAlarm(reminder)
        }
    }
