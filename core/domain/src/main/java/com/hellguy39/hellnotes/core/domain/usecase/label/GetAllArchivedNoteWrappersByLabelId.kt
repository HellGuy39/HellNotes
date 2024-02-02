package com.hellguy39.hellnotes.core.domain.usecase.label

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllArchivedNoteWrappersByLabelId
    @Inject
    constructor(
        private val getAllNoteWrappersFlowUseCase: GetAllNoteWrappersFlowUseCase,
        private val labelRepository: LabelRepository,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) {
        operator fun invoke(labelId: Long) =
            getAllNoteWrappersFlowUseCase.invoke()
                .combine(labelRepository.getLabelByIdFlow(labelId)) { noteWrappers, label ->
                    noteWrappers
                        .filter { wrapper -> wrapper.note.isArchived }
                        .filter { wrapper -> wrapper.labels.contains(label) }
                }
                .flowOn(ioDispatcher)
    }
