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
package com.hellguy39.hellnotes.core.domain.usecase.label

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.notes.LabelRepository
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
