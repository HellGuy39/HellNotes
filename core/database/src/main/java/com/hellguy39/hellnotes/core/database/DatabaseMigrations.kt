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

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hellguy39.hellnotes.core.database.entity.CHECKLIST_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.NOTES_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.REMINDERS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.REMINDS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.TRASH_TABLE_NAME

object DatabaseMigrations {
    @RenameTable(
        fromTableName = REMINDS_TABLE_NAME,
        toTableName = REMINDERS_TABLE_NAME,
    )
    @RenameColumn(
        tableName = NOTES_TABLE_NAME,
        fromColumnName = "lastEditDate",
        toColumnName = "edited_at",
    )
    @DeleteColumn(
        tableName = NOTES_TABLE_NAME,
        columnName = "labelIds",
    )
    class Schema2to3 : AutoMigrationSpec

    val Schema3to4 =
        object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `$CHECKLIST_TABLE_NAME` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT, 
                        `noteId` INTEGER NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `is_expanded` INTEGER NOT NULL, 
                        `items` TEXT NOT NULL
                    )
                    """.trimIndent(),
                )
            }
        }

    @DeleteTable(tableName = TRASH_TABLE_NAME)
    class Schema4to5 : AutoMigrationSpec
}
