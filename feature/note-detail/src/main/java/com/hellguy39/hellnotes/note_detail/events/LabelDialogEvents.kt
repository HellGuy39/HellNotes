package com.hellguy39.hellnotes.note_detail.events

import com.hellguy39.hellnotes.model.Label

interface LabelDialogEvents {
    fun show()
    fun dismiss()
    fun selectLabel(label: Label)
    fun unselectLabel(label: Label)
    fun createLabel(name: String)
    fun updateQuery(query: String)
}