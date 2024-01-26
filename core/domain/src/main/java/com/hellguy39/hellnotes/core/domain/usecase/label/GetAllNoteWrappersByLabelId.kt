package com.hellguy39.hellnotes.core.domain.usecase.label

import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllNoteWrappersByLabelId
    @Inject
    constructor(
        private val getAllNoteWrappersUseCase: GetAllNoteWrappersUseCase,
        private val labelRepository: LabelRepository,
    ) {
        operator fun invoke(labelId: Long) =
            getAllNoteWrappersUseCase.invoke()
                .combine(labelRepository.getLabelByIdFlow(labelId)) { noteWrappers, label ->
                    noteWrappers
                        .filter { wrapper -> !wrapper.note.isArchived }
                        .filter { wrapper -> wrapper.labels.contains(label) }
                }
                .flowOn(Dispatchers.IO)
    }
