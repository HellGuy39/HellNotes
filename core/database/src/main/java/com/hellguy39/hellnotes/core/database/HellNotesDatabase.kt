package com.hellguy39.hellnotes.core.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.hellguy39.hellnotes.core.database.converter.ChecklistConverter
import com.hellguy39.hellnotes.core.database.converter.LabelConverter
import com.hellguy39.hellnotes.core.database.converter.TrashConverter
import com.hellguy39.hellnotes.core.database.dao.*
import com.hellguy39.hellnotes.core.database.entity.*
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    version = 4,
    entities = [
        NoteEntity::class,
        ReminderEntity::class,
        LabelEntity::class,
        TrashEntity::class,
        ChecklistEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
    ],
    exportSchema = true,
)
@TypeConverters(LabelConverter::class, TrashConverter::class, ChecklistConverter::class)
abstract class HellNotesDatabase : RoomDatabase() {
    abstract val commonDao: CommonDao
    abstract val noteDao: NoteDao
    abstract val reminderDao: ReminderDao
    abstract val labelDao: LabelDao
    abstract val trashDao: TrashDao
    abstract val checklistDao: ChecklistDao

    suspend fun checkpoint() {
        commonDao.query((SimpleSQLiteQuery("pragma wal_checkpoint(full)")))
    }

    companion object {
        const val DATABASE_NAME = "hellnotes-database"

        @Volatile
        private var instance: HellNotesDatabase? = null

        fun getDatabase(
            @ApplicationContext context: Context,
        ): HellNotesDatabase {
            return instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context,
                        HellNotesDatabase::class.java,
                        DATABASE_NAME,
                    )
                        .addMigrations(DatabaseMigrations.Schema3to4)
                        .setJournalMode(JournalMode.TRUNCATE)
                        .build()
                this.instance = instance
                return instance
            }
        }
    }
}
