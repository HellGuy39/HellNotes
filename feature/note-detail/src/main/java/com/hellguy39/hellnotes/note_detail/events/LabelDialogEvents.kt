package com.hellguy39.hellnotes.note_detail.events

interface LabelDialogEvents {
    fun show()
    fun dismiss()
    fun selectLabel(labelId: Long)
    fun unselectLabel(labelId: Long)
    fun createLabel(name: String)
    fun updateQuery(query: String)
}