/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.domain.usecase

import com.hellguy39.hellnotes.core.domain.repository.notes.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.settings.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.usecase.reminder.DeleteReminderUseCase
import javax.inject.Inject

class ResetAppUseCase
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val checklistRepository: ChecklistRepository,
        private val reminderRepository: ReminderRepository,
        private val deleteReminderUseCase: DeleteReminderUseCase,
    ) {
        suspend operator fun invoke(
            onResetDatabase: Boolean,
            onResetSettings: Boolean,
        ) {
            if (onResetDatabase) {
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

            reminderRepository.getAllReminders().forEach { reminder ->
                deleteReminderUseCase.invoke(reminder.id ?: return)
            }
        }

        private suspend fun resetSettings() {
            dataStoreRepository.resetToDefault()
        }
    }
