package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.common.date.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.use_case.reminder.CreateReminderUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CopyNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val createReminderUseCase: CreateReminderUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(noteWrapper: NoteWrapper): Long {
        return withContext(ioDispatcher) {
            val copiedNoteId = noteRepository.insertNote(
                noteWrapper.note.copy(
                    id = null,
                    createdAt = System.currentTimeMillis()
                )
            )

            val copiedLabels = noteWrapper.labels.toMutableList().apply {
                for (i in this.indices) {
                    val label = this[i]
                    this[i] = label.copy(noteIds = label.noteIds.plus(copiedNoteId))
                }
            }

            labelRepository.updateLabels(copiedLabels)

            val copiedChecklists = noteWrapper.checklists.toMutableList().apply {
                for (i in this.indices) {
                    val checklist = this[i]
                    this[i] = checklist.copy(
                        id = null,
                        noteId = copiedNoteId
                    )
                }
            }

            copiedChecklists.forEach { checklist ->
                checklistRepository.insertChecklist(checklist)
            }

            noteWrapper.reminders.forEach { reminder ->
                createReminderUseCase.invoke(reminder.copy(id = null, noteId = copiedNoteId))
            }

            copiedNoteId
        }
    }
}