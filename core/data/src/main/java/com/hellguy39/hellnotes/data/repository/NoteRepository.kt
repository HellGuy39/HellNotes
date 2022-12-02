package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNoteById(id: Int)

}