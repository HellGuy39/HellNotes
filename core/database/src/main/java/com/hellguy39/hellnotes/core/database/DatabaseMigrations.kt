package com.hellguy39.hellnotes.core.database

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hellguy39.hellnotes.core.database.entity.CHECKLIST_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.NOTES_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.REMINDERS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.REMINDS_TABLE_NAME

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
}
