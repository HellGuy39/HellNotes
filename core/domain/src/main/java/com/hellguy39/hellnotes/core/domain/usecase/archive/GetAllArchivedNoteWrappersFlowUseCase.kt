package com.hellguy39.hellnotes.core.domain.usecase.archive

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllArchivedNoteWrappersFlowUseCase
    @Inject
    constructor(
        private val getAllNoteWrappersFlowUseCase: GetAllNoteWrappersFlowUseCase,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) {
        operator fun invoke() =
            getAllNoteWrappersFlowUseCase.invoke()
                .map { noteWrappers ->
                    noteWrappers.filter { noteWrapper -> noteWrapper.note.isArchived }
                }
                .flowOn(ioDispatcher)
    }
