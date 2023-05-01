package com.hellguy39.hellnotes.core.domain.use_case

import com.hellguy39.hellnotes.core.domain.repository.local.*
import com.hellguy39.hellnotes.core.domain.use_case.reminder.DeleteReminderUseCase
import javax.inject.Inject

class ResetAppUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository,
    private val deleteReminderUseCase: DeleteReminderUseCase
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
        noteRepository.deleteAll()
        labelRepository.deleteAll()
        checklistRepository.deleteAll()
        trashRepository.deleteAll()

        reminderRepository.getAllReminders().forEach { reminder ->
            deleteReminderUseCase.invoke(reminder.id ?: return)
        }
    }

    private suspend fun resetSettings() {
        dataStoreRepository.resetToDefault()
    }
}