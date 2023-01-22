package com.hellguy39.hellnotes.core.model

data class NoteDetailWrapper(
    val note: Note,
    val labels: List<Label>,
    val reminders: List<Remind>
)