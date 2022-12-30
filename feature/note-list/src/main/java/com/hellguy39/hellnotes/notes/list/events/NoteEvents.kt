package com.hellguy39.hellnotes.notes.list.events

import com.hellguy39.hellnotes.model.Note

interface NoteEvents {
    fun onClick(note: Note)
    fun onLongClick(note: Note)
}