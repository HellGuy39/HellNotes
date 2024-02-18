package com.hellguy39.hellnotes.core.domain.usecase.label

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.usecase.trash.GetAllNoteWrappersAtTrashFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllNoteWrappersAtTrashByLabelId
    @Inject
    constructor(
        private val getAllNoteWrappersAtTrashFlowUseCase: GetAllNoteWrappersAtTrashFlowUseCase,
        private val labelRepository: LabelRepository,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) {
        operator fun invoke(labelId: Long) =
            getAllNoteWrappersAtTrashFlowUseCase.invoke()
                .combine(labelRepository.getLabelByIdFlow(labelId)) { noteWrappers, label ->
                    noteWrappers
                        .filter { wrapper -> wrapper.labels.contains(label) }
                }
                .flowOn(ioDispatcher)
    }
