package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import javax.inject.Inject

class PostProcessNoteUseCase @Inject constructor() {

    operator fun invoke(wrapper: NoteDetailWrapper): NoteDetailWrapper {
        return wrapper.copy(
            note = wrapper.note.postProcessNote(),
            checklists = wrapper.checklists.postProcessChecklists()
        )
    }

    private fun Note.postProcessNote(): Note {
        return this.copy(
            title = this.title.trim(),
            note = this.note.trim()
        )
    }

    private fun List<Checklist>.postProcessChecklists(): List<Checklist> {
        return this.toMutableList().apply {
            for (i in this.indices) {
                this[i] = this[i].postProcessChecklist()
            }
        }
    }

    private fun Checklist.postProcessChecklist(): Checklist {
        return this.copy(
            name = name.trim(),
            items = items.postProcessChecklistItems()
        )
    }

    private fun List<ChecklistItem>.postProcessChecklistItems(): List<ChecklistItem> {
        return this.toMutableList().apply {
            for (i in this.indices) {
                this[i] = this[i].postProcessChecklistItem()
            }
        }
    }

    private fun ChecklistItem.postProcessChecklistItem(): ChecklistItem {
        return this.copy(
            text = this.text.trim()
        )
    }

}