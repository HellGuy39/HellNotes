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
package com.hellguy39.hellnotes.core.domain.usecase.trash

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeleteAllExpiredNoteWrappersAtTrashUseCase
    @Inject
    constructor(
        private val deleteNoteUseCase: DeleteNoteUseCase,
        private val getAllNoteWrappersAtTrashUseCase: GetAllNoteWrappersAtTrashUseCase,
    ) {
        suspend operator fun invoke() {
            val noteWrappersAtTrash = getAllNoteWrappersAtTrashUseCase.invoke()
            noteWrappersAtTrash.forEach { noteWrapper ->
                val dateOfExpiration = noteWrapper.note.editedAt + TimeUnit.DAYS.toMillis(7)
                if (System.currentTimeMillis() > dateOfExpiration) {
                    deleteNoteUseCase.invoke(noteWrapper.note.id)
                }
            }
        }
    }
