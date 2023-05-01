package com.hellguy39.hellnotes.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTES_TABLE_NAME = "notes_table"

@Entity(tableName = NOTES_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val title: String,

    val note: String,

    @ColumnInfo(name = "edited_at", defaultValue = "0")
    val editedAt: Long,

    val isPinned: Boolean,

    @ColumnInfo(name = "is_archived", defaultValue = "0")
    val isArchived: Boolean,

    @ColumnInfo(name = "created_at", defaultValue = "0")
    val createdAt: Long,

    val colorHex: Long,
)