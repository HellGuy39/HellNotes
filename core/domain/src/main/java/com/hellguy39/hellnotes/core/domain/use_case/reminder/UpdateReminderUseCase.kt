package com.hellguy39.hellnotes.core.domain.use_case.reminder

import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import com.hellguy39.hellnotes.core.model.Reminder
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(reminder: Reminder) {
        val oldReminder = reminderRepository.getReminderById(reminder.id ?: return)
        reminderRepository.updateReminder(reminder)

        alarmScheduler.cancelAlarm(oldReminder)
        alarmScheduler.scheduleAlarm(reminder)
    }
}