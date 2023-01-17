package com.hellguy39.hellnotes.core.data.repository

import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.database.dao.NoteDao
import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import com.hellguy39.hellnotes.core.database.mapper.toNote
import com.hellguy39.hellnotes.core.database.mapper.toNoteEntity
import com.hellguy39.hellnotes.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {

    override fun getAllNotesStream(): Flow<List<Note>>  =
        noteDao.getAllNotesStream().map { it.map(NoteEntity::toNote) }

    override fun getAllNotesWithQueryStream(query: String): Flow<List<Note>> =
        noteDao.getAllNotesByQueryStream(query).map { it.map(NoteEntity::toNote) }

    override fun getNoteByIdStream(id: Long): Flow<Note> {
        return noteDao.getNoteByIdStream(id).map(NoteEntity::toNote)
    }

    override suspend fun getAllNotes(): List<Note> =
        noteDao.getAllNotes().map(NoteEntity::toNote)

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

    override suspend fun deleteLabelFromNotes(labelId: Long) {
        val notes = noteDao.getAllNotes()

        for (i in notes.indices) {
            if (notes[i].labelIds.contains(labelId)) {
                val note = notes[i].copy(
                    labelIds = notes[i].labelIds.minus(labelId)
                )
                noteDao.updateNote(note)
            }
        }

    }

}