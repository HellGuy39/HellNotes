package com.hellguy39.hellnotes.domain.repository

import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.Sorting
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllNotesStream(): Flow<List<Note>>

    suspend fun getAllNotesWithQueryStream(query: String): Flow<List<Note>>

    suspend fun getNoteById(id: Long): Note

    suspend fun insertNote(note: Note): Long

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotes(notes: List<Note>)

    suspend fun deleteNoteById(id: Long)

}