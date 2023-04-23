package com.hellguy39.hellnotes.core.data.database

import android.content.Context
import android.net.Uri
import com.hellguy39.hellnotes.core.database.HellNotesDatabase
import com.hellguy39.hellnotes.core.domain.database.BackupManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class BackupManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : BackupManager {

    override suspend fun restore(filepath: Uri) {
        val contentResolver = context.contentResolver
        val database = HellNotesDatabase.getDatabase(context)

        val databaseFile = File(database.openHelper.writableDatabase.path.toString())

        contentResolver.openInputStream(filepath)?.use { stream ->
            databaseFile.writeBytes(stream.readBytes())
        }
    }

    override suspend fun backup(filepath: Uri) {
        val contentResolver = context.contentResolver
        val database = HellNotesDatabase.getDatabase(context)

        val databaseFile = File(database.openHelper.writableDatabase.path.toString())

        contentResolver.openOutputStream(filepath)?.use { stream ->
            stream.write(databaseFile.readBytes())
        }
    }

}