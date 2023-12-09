package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.tools.AlarmScheduler
import javax.inject.Inject

class DeleteReminderUseCase
    @Inject
    constructor(
        private val reminderRepository: ReminderRepository,
        private val alarmScheduler: AlarmScheduler,
    ) {
        suspend operator fun invoke(id: Long) {
            val reminder = reminderRepository.getReminderById(id)

            reminderRepository.deleteReminder(reminder)
            alarmScheduler.cancelAlarm(reminder)
        }
    }
