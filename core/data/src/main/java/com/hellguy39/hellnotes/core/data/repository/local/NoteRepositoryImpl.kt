/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.database.dao.NoteDao
import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import com.hellguy39.hellnotes.core.database.mapper.toNote
import com.hellguy39.hellnotes.core.database.mapper.toNoteEntity
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteRepository
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
