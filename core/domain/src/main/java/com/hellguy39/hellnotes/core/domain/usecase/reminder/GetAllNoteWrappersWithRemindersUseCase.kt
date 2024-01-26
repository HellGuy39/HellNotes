package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNoteWrappersWithRemindersUseCase
    @Inject
    constructor(
        private val getAllNoteWrappersUseCase: GetAllNoteWrappersUseCase,
    ) {
        operator fun invoke() =
            getAllNoteWrappersUseCase.invoke()
                .map { noteWrappers ->
                    noteWrappers
                        .filter { wrapper -> wrapper.reminders.isNotEmpty() }
                        .sortedBy { wrapper -> wrapper.reminders.first().triggerDate }
                }
                .flowOn(Dispatchers.IO)
    }
