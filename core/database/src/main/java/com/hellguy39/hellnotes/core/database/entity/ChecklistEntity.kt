package com.hellguy39.hellnotes.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem

const val CHECKLIST_TABLE_NAME = "checklist_table"

@Entity(tableName = CHECKLIST_TABLE_NAME)
data class ChecklistEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val noteId: Long,
    val name: String,
    val items: List<ChecklistItem>
)
