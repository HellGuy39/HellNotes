package com.hellguy39.hellnotes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hellguy39.hellnotes.core.database.dao.LabelDao
import com.hellguy39.hellnotes.core.database.dao.NoteDao
import com.hellguy39.hellnotes.core.database.dao.RemindDao
import com.hellguy39.hellnotes.core.database.converter.LabelConverter
import com.hellguy39.hellnotes.core.database.converter.TrashConverter
import com.hellguy39.hellnotes.core.database.dao.TrashDao
import com.hellguy39.hellnotes.core.database.entity.*

@Database(
    version = 1,
    entities = [
        NoteEntity::class,
        RemindEntity::class,
        LabelEntity::class,
        TrashEntity::class
    ],
    exportSchema = true
)
@TypeConverters(LabelConverter::class, TrashConverter::class)
abstract class HellNotesDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val remindDao: RemindDao
    abstract val labelDao: LabelDao
    abstract val trashDao: TrashDao

    companion object {
        const val DATABASE_NAME = "hellnotes-database"
    }
}