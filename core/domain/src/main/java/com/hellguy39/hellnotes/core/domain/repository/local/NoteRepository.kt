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
package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.NoteEditor
import com.hellguy39.hellnotes.core.model.repository.local.database.edit
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotesStream(): Flow<List<Note>>

    fun getNoteByIdStream(id: Long): Flow<Note?>

    suspend fun getAllNotes(): List<Note>

    suspend fun getNoteById(id: Long): Note?

    suspend fun insertNote(note: Note): Long

    suspend fun updateNote(note: Note)

    suspend fun updateNotes(notes: List<Note>)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotes(notes: List<Note>)

    suspend fun deleteNoteById(id: Long)

    suspend fun deleteAll()
}

suspend fun NoteRepository.findByIdAndEdit(noteId: Long?, editor: NoteEditor) {
    if (noteId == null) return
    val note = getNoteById(noteId) ?: return
    val editedNote = note.edit(editor)
    updateNote(editedNote)
}
