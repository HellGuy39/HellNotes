package com.hellguy39.hellnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.entity.NoteEntity

@Database(
    version = 1,
    entities = [
        NoteEntity::class
    ],
    exportSchema = true
)
abstract class HellNotesDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
}