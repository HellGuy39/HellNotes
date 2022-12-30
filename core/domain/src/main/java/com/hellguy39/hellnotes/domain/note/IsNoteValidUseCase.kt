package com.hellguy39.hellnotes.domain.note

import com.hellguy39.hellnotes.model.Note
import javax.inject.Inject

class IsNoteValidUseCase @Inject constructor() {

    operator fun invoke(note: Note): Boolean {

        var isTitleEmpty = false
        var isTextEmpty = false

        note.title.let { title ->

            if (title == null) {
                isTitleEmpty = true
                return@let
            }

            if (!title.isTextValid()) {
                isTitleEmpty = true
                return@let
            }

        }

        note.note.let { text ->

            if (text == null) {
                isTextEmpty = true
                return@let
            }

            if (!text.isTextValid()) {
                isTextEmpty = true
                return@let
            }

        }

        return !(isTitleEmpty && isTextEmpty)
    }

    private fun String.isTextValid() = this.isNotEmpty() && this.isNotBlank()

}