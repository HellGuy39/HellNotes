package com.hellguy39.hellnotes.core.domain.use_case.app

import com.hellguy39.hellnotes.core.common.date.di.IoDispatcher
import com.hellguy39.hellnotes.core.common.date.di.MainDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.*
import com.hellguy39.hellnotes.core.domain.use_case.reminder.DeleteReminderUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetAppUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        onResetDatabase: Boolean,
        onResetSettings: Boolean
    ) {
        if(onResetDatabase) {
            resetDatabase()
        }
        if (onResetSettings) {
            resetSettings()
        }
    }

    private suspend fun resetDatabase() {
        withContext(ioDispatcher) {
            noteRepository.deleteAll()
            labelRepository.deleteAll()
            checklistRepository.deleteAll()
            trashRepository.deleteAll()

            reminderRepository.getAllReminders()
                .forEach { reminder -> deleteReminderUseCase.invoke(reminder.id) }
        }
    }

    private suspend fun resetSettings() {
        withContext(ioDispatcher) {
            dataStoreRepository.resetToDefault()
        }
    }
}