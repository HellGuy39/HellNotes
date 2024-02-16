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
    val colorHex: Long = ColorParam.DefaultColor,
) {
    val hasContentText: Boolean
        get() = note.isNotBlank()

    val isValid: Boolean
        get() = note.isNotBlank() || title.isNotBlank()

}

class NoteBuilder internal constructor() {

    private var editedAt: Long = System.currentTimeMillis()
    private var createdAt: Long = System.currentTimeMillis()
    private var colorHex: Long = ColorParam.DefaultColor
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
