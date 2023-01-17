package com.hellguy39.hellnotes.feature.note_detail.util

import com.hellguy39.hellnotes.core.model.Note

internal fun Note.isNoteValid(): Boolean {
    var isTitleEmpty = false
    var isTextEmpty = false

    this.title.let { title ->

        if (!title.isTextValid()) {
            isTitleEmpty = true
            return@let
        }

    }

    this.note.let { text ->

        if (!text.isTextValid()) {
            isTextEmpty = true
            return@let
        }

    }

    return !(isTitleEmpty && isTextEmpty)
}

private fun String.isTextValid() = this.isNotEmpty() && this.isNotBlank()
