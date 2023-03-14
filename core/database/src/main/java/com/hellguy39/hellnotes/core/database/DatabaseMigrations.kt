package com.hellguy39.hellnotes.core.database

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec
import com.hellguy39.hellnotes.core.database.entity.NOTES_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.REMINDERS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.REMINDS_TABLE_NAME

object DatabaseMigrations {

    @RenameTable(
        fromTableName = REMINDS_TABLE_NAME,
        toTableName = REMINDERS_TABLE_NAME
    )
    @RenameColumn(
        tableName = NOTES_TABLE_NAME,
        fromColumnName = "lastEditDate",
        toColumnName = "edited_at",
    )
    @DeleteColumn(
        tableName = NOTES_TABLE_NAME,
        columnName = "labelIds"
    )
    class Schema2to3 : AutoMigrationSpec


}