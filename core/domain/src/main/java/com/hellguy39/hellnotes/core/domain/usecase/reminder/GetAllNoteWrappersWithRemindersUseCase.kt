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
package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNoteWrappersWithRemindersUseCase
    @Inject
    constructor(
        private val getAllNoteWrappersFlowUseCase: GetAllNoteWrappersFlowUseCase,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) {
        operator fun invoke() =
            getAllNoteWrappersFlowUseCase.invoke()
                .map { noteWrappers ->
                    noteWrappers
                        .filter { wrapper -> wrapper.reminders.isNotEmpty() }
                        .sortedBy { wrapper -> wrapper.reminders.first().triggerDate }
                }
                .flowOn(ioDispatcher)
    }
