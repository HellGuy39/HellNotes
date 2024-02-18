package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.tools.AlarmScheduler
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import javax.inject.Inject

class CreateReminderUseCase
    @Inject
    constructor(
        private val reminderRepository: ReminderRepository,
        private val alarmScheduler: AlarmScheduler,
    ) {
        suspend operator fun invoke(reminder: Reminder) {
            val id = reminderRepository.insertReminder(reminder)
            alarmScheduler.scheduleAlarm(reminder.copy(id = id))
        }
    }
