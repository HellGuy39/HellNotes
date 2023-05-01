package com.hellguy39.hellnotes.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.core.model.repository.local.database.Note

const val TRASH_TABLE_NAME = "trash_table"

@Entity(tableName = TRASH_TABLE_NAME)
data class TrashEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val note: Note,
    val dateOfAdding: Long
)
