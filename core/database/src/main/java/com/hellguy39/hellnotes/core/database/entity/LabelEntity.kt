package com.hellguy39.hellnotes.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val LABELS_TABLE_NAME = "labels_table"

@Entity(tableName = LABELS_TABLE_NAME)
data class LabelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String
)
