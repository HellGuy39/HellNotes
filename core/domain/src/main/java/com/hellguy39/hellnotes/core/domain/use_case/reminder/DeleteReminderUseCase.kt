package com.hellguy39.hellnotes.core.domain.use_case.reminder

import com.hellguy39.hellnotes.core.common.date.di.IoDispatcher
import com.hellguy39.hellnotes.core.common.date.di.MainDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(id: Long?) {

        requireNotNull(id)

        withContext(ioDispatcher) {
            val reminder = reminderRepository.getReminderById(id)
            reminderRepository.deleteReminder(reminder)
            withContext(mainDispatcher) {
                alarmScheduler.cancelAlarm(reminder)
            }
        }
    }
}