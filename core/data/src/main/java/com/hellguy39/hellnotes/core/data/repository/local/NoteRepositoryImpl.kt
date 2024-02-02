package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.database.dao.NoteDao
import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import com.hellguy39.hellnotes.core.database.mapper.toNote
import com.hellguy39.hellnotes.core.database.mapper.toNoteEntity
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl
    @Inject
    constructor(
        private val noteDao: NoteDao,
    ) : NoteRepository {
        override fun getAllNotesStream(): Flow<List<Note>> =
            noteDao.getAllFlow()
                .map { it.map(NoteEntity::toNote) }

        override fun getNoteByIdStream(id: Long): Flow<Note?> {
            return noteDao.findByIdFlow(id)
                .map { it?.toNote() }
        }

        override suspend fun getAllNotes(): List<Note> = noteDao.getAll().map(NoteEntity::toNote)

        override suspend fun getNoteById(id: Long): Note? = noteDao.findById(id)?.toNote()

        override suspend fun insertNote(note: Note): Long {
            return noteDao.insert(note.toNoteEntity())
        }

        override suspend fun updateNote(note: Note) {
            noteDao.update(note.toNoteEntity())
        }

        override suspend fun updateNotes(notes: List<Note>) {
            noteDao.update(notes.map(Note::toNoteEntity))
        }

        override suspend fun deleteNote(note: Note) {
            noteDao.delete(note.toNoteEntity())
        }

        override suspend fun deleteNotes(notes: List<Note>) {
            noteDao.delete(notes.map { it.toNoteEntity() })
        }

        override suspend fun deleteNoteById(id: Long) {
            noteDao.deleteById(id)
        }

        override suspend fun deleteAll() {
            noteDao.deleteAll()
        }
    }
