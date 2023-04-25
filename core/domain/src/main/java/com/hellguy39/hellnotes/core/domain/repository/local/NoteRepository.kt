package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotesStream(): Flow<List<Note>>


    fun getNoteByIdStream(id: Long): Flow<Note>

    suspend fun getAllNotes(): List<Note>

    suspend fun getNoteById(id: Long): Note

    suspend fun insertNote(note: Note): Long

    suspend fun updateNote(note: Note)

    suspend fun updateNotes(notes: List<Note>)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotes(notes: List<Note>)

    suspend fun deleteNoteById(id: Long)

    suspend fun deleteAll()
}