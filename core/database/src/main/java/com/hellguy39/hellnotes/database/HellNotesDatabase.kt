package com.hellguy39.hellnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hellguy39.hellnotes.database.dao.LabelDao
import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.dao.RemindDao
import com.hellguy39.hellnotes.database.entity.LabelEntity
import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.database.entity.RemindEntity
import com.hellguy39.hellnotes.database.converter.LabelConverter

@Database(
    version = 1,
    entities = [
        NoteEntity::class,
        RemindEntity::class,
        LabelEntity::class
    ],
    exportSchema = true
)
@TypeConverters(LabelConverter::class)
abstract class HellNotesDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val remindDao: RemindDao
    abstract val labelDao: LabelDao

    companion object {
        const val DATABASE_NAME = "hellnotes-database"
    }
}