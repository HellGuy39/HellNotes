package com.hellguy39.hellnotes.core.domain.usecase.trash

import javax.inject.Inject

class DeleteAllNoteWrappersAtTrashUseCase
    @Inject
    constructor(
        private val deleteNoteUseCase: DeleteNoteUseCase,
        private val getAllNoteWrappersAtTrashUseCase: GetAllNoteWrappersAtTrashUseCase,
    ) {
        suspend operator fun invoke() {
            val noteWrappersAtTrash = getAllNoteWrappersAtTrashUseCase.invoke()
            noteWrappersAtTrash.forEach { noteWrapper ->
                deleteNoteUseCase.invoke(noteWrapper.note.id)
            }
        }
    }
