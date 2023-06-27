package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import javax.inject.Inject

class ArchiveNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteWrappers: List<NoteWrapper>, isArchived: Boolean) {
        val updatedNotes = mutableListOf<Note>()
        noteWrappers.forEach { wrapper ->
            updatedNotes.add(wrapper.note.copy(isArchived = isArchived))
        }
        noteRepository.updateNotes(updatedNotes)
    }

}