package com.hellguy39.hellnotes.core.database.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface CommonDao {
    @RawQuery
    suspend fun query(supportSQLiteQuery: SupportSQLiteQuery): Int
}
