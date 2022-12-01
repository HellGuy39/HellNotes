package com.hellguy39.hellnotes.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.model.Note

const val NOTES_TABLE_NAME = "notes_table"

@Entity(tableName = NOTES_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey val id: Int? = null,
    val title: String?,
    val note: String?,
    val lastEditDate: Long?,
    val isPinned: Boolean?,
    val labels: List<String>?
)