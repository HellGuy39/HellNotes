package com.hellguy39.hellnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.database.util.LabelConverter

@Database(
    version = 1,
    entities = [
        NoteEntity::class
    ],
    exportSchema = true
)
@TypeConverters(LabelConverter::class)
abstract class HellNotesDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
}