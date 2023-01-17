package com.hellguy39.hellnotes.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.util.ColorParam

const val NOTES_TABLE_NAME = "notes_table"

@Entity(tableName = NOTES_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String,
    val note: String,
    val lastEditDate: Long,
    val isPinned: Boolean,
    val colorHex: Long,
    val labelIds: List<Long>
)