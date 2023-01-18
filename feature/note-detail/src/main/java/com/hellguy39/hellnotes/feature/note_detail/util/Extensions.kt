package com.hellguy39.hellnotes.feature.note_detail.util

import com.hellguy39.hellnotes.core.model.Note

internal fun Note.isNoteValid(): Boolean {
    return note.isTextValid() || title.isTextValid()
}

private fun String.isTextValid() = this.isNotEmpty() && this.isNotBlank()
