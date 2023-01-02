package com.hellguy39.hellnotes.note_detail.events

import com.hellguy39.hellnotes.model.Note

interface ShareDialogEvents {
    fun show()
    fun dismiss()
    fun shareAsTxtFile(note: Note)
    fun shareAsPlainText(note: Note)
}