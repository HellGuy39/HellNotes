package com.hellguy39.hellnotes.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ColorParam

const val REMINDS_TABLE_NAME = "reminds_table"

@Entity(tableName = REMINDS_TABLE_NAME)
data class RemindEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val noteId: Int,
    val message: Int,
    val triggerDate: Long
)