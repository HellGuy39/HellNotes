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
package com.hellguy39.hellnotes.core.model.repository.local.database

import com.hellguy39.hellnotes.core.model.ColorParam

data class Note(
    val id: Long? = null,
    val title: String = "",
    val note: String = "",
    val editedAt: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val isPinned: Boolean = false,
    val atTrash: Boolean = false,
    val colorHex: Long = ColorParam.DEFAULT_COLOR,
) {
    val hasContentText: Boolean
        get() = note.isNotBlank()

    val isValid: Boolean
        get() = note.isNotBlank() || title.isNotBlank()
}

class NoteBuilder internal constructor() {
    private var editedAt: Long = System.currentTimeMillis()
    private var createdAt: Long = System.currentTimeMillis()
    private var colorHex: Long = ColorParam.DEFAULT_COLOR
    private var id: Long? = null

    var title: String = ""
    var note: String = ""
    var isArchived: Boolean = false
    var isPinned: Boolean = false
    var atTrash: Boolean = false

    fun commit() {
        editedAt = System.currentTimeMillis()
    }

    fun build(): Note {
        return Note(
            id = id,
            title = title,
            note = note,
            editedAt = editedAt,
            createdAt = createdAt,
            isArchived = isArchived,
            isPinned = isPinned,
            atTrash = atTrash,
            colorHex = colorHex,
        )
    }

    companion object {
        fun from(note: Note): NoteBuilder {
            return NoteBuilder().apply {
                this.id = note.id
                this.title = note.title
                this.note = note.note
                this.editedAt = note.editedAt
                this.createdAt = note.createdAt
                this.isArchived = note.isArchived
                this.isPinned = note.isPinned
                this.atTrash = note.atTrash
                this.colorHex = note.colorHex
            }
        }
    }
}

typealias NoteEditor = NoteBuilder.() -> Unit

fun Note.edit(editor: NoteEditor): Note {
    val builder = NoteBuilder.from(this)
    builder.editor()
    builder.commit()
    return builder.build()
}
