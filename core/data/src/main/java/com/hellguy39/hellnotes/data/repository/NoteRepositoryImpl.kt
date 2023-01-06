package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.domain.repository.NoteRepository
import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.database.mapper.toNote
import com.hellguy39.hellnotes.database.mapper.toNoteEntity
import com.hellguy39.hellnotes.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {

    override suspend fun getAllNotesStream(): Flow<List<Note>>  =
        noteDao.getAllNotes().map { it.map(NoteEntity::toNote) }

    override suspend fun getAllNotesWithQueryStream(query: String): Flow<List<Note>> =
        noteDao.getAllNotesByQuery(query).map { it.map(NoteEntity::toNote) }

    override suspend fun getNoteById(id: Long): Note =
        noteDao.getNoteById(id).toNote()

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toNoteEntity())
    }

    override suspend fun deleteNotes(notes: List<Note>) {
        noteDao.deleteNotes(notes.map { it.toNoteEntity() })
    }

    override suspend fun deleteNoteById(id: Long) {
        noteDao.deleteNoteById(id)
    }

}