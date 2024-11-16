/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    version = 5,
    entities = [
        NoteEntity::class,
        ReminderEntity::class,
        LabelEntity::class,
        ChecklistEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
        AutoMigration(from = 4, to = 5, spec = DatabaseMigrations.Schema4to5::class),
    ],
    exportSchema = true,
)
@TypeConverters(LabelConverter::class, TrashConverter::class, ChecklistConverter::class)
abstract class HellNotesDatabase : RoomDatabase() {
    abstract fun commonDao(): CommonDao

    abstract fun noteDao(): NoteDao

    abstract fun reminderDao(): ReminderDao

    abstract fun labelDao(): LabelDao

    abstract fun checklistDao(): ChecklistDao

    suspend fun checkpoint() {
        commonDao().query((SimpleSQLiteQuery("pragma wal_checkpoint(full)")))
    }

    companion object {
        private const val DATABASE_NAME = "hellnotes-database"

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
