package com.hellguy39.hellnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.dao.RemindDao
import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.database.entity.RemindEntity
import com.hellguy39.hellnotes.database.util.LabelConverter
import com.hellguy39.hellnotes.model.Remind

@Database(
    version = 1,
    entities = [
        NoteEntity::class,
        RemindEntity::class
    ],
    exportSchema = true
)
@TypeConverters(LabelConverter::class)
abstract class HellNotesDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val remindDao: RemindDao

    companion object {
        const val DATABASE_NAME = "hellnotes-database"
    }
}