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
