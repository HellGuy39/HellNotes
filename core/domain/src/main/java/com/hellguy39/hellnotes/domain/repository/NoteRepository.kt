package com.hellguy39.hellnotes.domain.repository

import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.Sorting
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotesStream(): Flow<List<Note>>

    fun getAllNotesWithQueryStream(query: String): Flow<List<Note>>

    suspend fun getAllNotes(): List<Note>

    suspend fun getNoteById(id: Long): Note

    suspend fun insertNote(note: Note): Long

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotes(notes: List<Note>)

    suspend fun deleteNoteById(id: Long)

    suspend fun deleteLabelFromNotes(labelId: Long)

}