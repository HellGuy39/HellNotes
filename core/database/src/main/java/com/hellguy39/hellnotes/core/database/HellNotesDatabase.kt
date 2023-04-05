package com.hellguy39.hellnotes.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hellguy39.hellnotes.core.database.converter.ChecklistConverter
import com.hellguy39.hellnotes.core.database.converter.LabelConverter
import com.hellguy39.hellnotes.core.database.converter.TrashConverter
import com.hellguy39.hellnotes.core.database.dao.*
import com.hellguy39.hellnotes.core.database.entity.*

@Database(
    version = 4,
    entities = [
        NoteEntity::class,
        ReminderEntity::class,
        LabelEntity::class,
        TrashEntity::class,
        ChecklistEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
    ],
    exportSchema = true
)
@TypeConverters(LabelConverter::class, TrashConverter::class, ChecklistConverter::class)
abstract class HellNotesDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val reminderDao: ReminderDao
    abstract val labelDao: LabelDao
    abstract val trashDao: TrashDao
    abstract val checklistDao: ChecklistDao

    companion object {
        const val DATABASE_NAME = "hellnotes-database"
    }
}