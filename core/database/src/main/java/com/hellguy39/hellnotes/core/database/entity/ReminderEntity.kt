package com.hellguy39.hellnotes.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat

const val REMINDS_TABLE_NAME = "reminds_table"
const val REMINDERS_TABLE_NAME = "reminders_table"

@Entity(tableName = REMINDERS_TABLE_NAME)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val noteId: Long,
    val message: String,
    val triggerDate: Long,
    @ColumnInfo(name = "repeat", defaultValue = Repeat.DOES_NOT_REPEAT)
    val repeat: String
)