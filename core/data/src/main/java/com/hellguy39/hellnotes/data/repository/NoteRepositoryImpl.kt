package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.database.util.toNote
import com.hellguy39.hellnotes.database.util.toNoteEntity
import com.hellguy39.hellnotes.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {

    override suspend fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { it.map(NoteEntity::toNote) }

    override suspend fun getNoteById(id: Int): Note =
        noteDao.getNoteById(id).toNote()

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toNoteEntity())
    }

    override suspend fun deleteNoteById(id: Int) {
        noteDao.deleteNoteById(id)
    }

}